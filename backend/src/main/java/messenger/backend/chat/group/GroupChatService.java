package messenger.backend.chat.group;

import lombok.RequiredArgsConstructor;
import messenger.backend.auth.jwt.JwtTokenService;
import messenger.backend.chat.GroupChatEntity;
import messenger.backend.chat.exceptions.ChatNotFoundException;
import messenger.backend.chat.exceptions.UserAlreadyMemberOfChatException;
import messenger.backend.chat.exceptions.UserNotMemberOfChatException;
import messenger.backend.chat.group.dto.*;
import messenger.backend.chat.group.exceptions.NotEnoughPermissionLevelException;
import messenger.backend.chat.group.exceptions.UserNotInChatException;
import messenger.backend.chat.group.exceptions.UserNotOwnerOfChatException;
import messenger.backend.user.UserEntity;
import messenger.backend.user.UserRepository;
import messenger.backend.user.dto.UserSearchInfoDto;
import messenger.backend.user.exceptions.UserNotFoundException;
import messenger.backend.userChat.UserChat;
import messenger.backend.userChat.UserChatRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RequiredArgsConstructor

@Service
public class GroupChatService {

    private final GroupChatRepository groupChatRepository;
    private final UserChatRepository userChatRepository;
    private final UserRepository userRepository;


    public CreateGroupChatResponseDto createGroupChat(CreateGroupChatRequestDto requestDto) {
        UserEntity contextUser = JwtTokenService.getContextUser();

        GroupChatEntity groupChat = GroupChatEntity.builder().groupName(requestDto.getChatName()).build();

        groupChat = groupChatRepository.saveAndFlush(groupChat);

        UserChat contextUserChat = UserChat.builder()
                .user(contextUser)
                .chat(groupChat)
                .permissionLevel(UserChat.PermissionLevel.OWNER)
                .build();

        userChatRepository.saveAndFlush(contextUserChat);

        CreateGroupChatResponseDto responseDto = new CreateGroupChatResponseDto();
        responseDto.setChatId(groupChat.getChatId());
        return responseDto;
    }

    public void deleteGroupChat(DeleteGroupChatRequestDto requestDto) {
        GroupChatEntity groupChatEntity = groupChatRepository.findByIdWithFetch(requestDto.getChatId())
                .orElseThrow(ChatNotFoundException::new);

        UserEntity contextUser = JwtTokenService.getContextUser();
        boolean isUserOwnerOfChat = groupChatEntity.getUserChats().stream()
                .anyMatch(chat -> chat.getPermissionLevel().equals(UserChat.PermissionLevel.OWNER)
                        && chat.getUser().getId().equals(contextUser.getId()));
        if (!isUserOwnerOfChat) throw new UserNotOwnerOfChatException();

        groupChatRepository.delete(groupChatEntity);
    }

    public void addMemberToChat(AddMemberToGroupChatRequestDto requestDto) {
        GroupChatEntity groupChatEntity = groupChatRepository.findByIdWithFetch(requestDto.getChatId())
                .orElseThrow(ChatNotFoundException::new);

        UserEntity targetUser = userRepository.findById(requestDto.getTargetUserId())
                .orElseThrow(UserNotFoundException::new);

        UserEntity contextUser = JwtTokenService.getContextUser();

        boolean isContextUserAdminOrOwner = groupChatEntity.getUserChats().stream()
                .anyMatch(userChat -> userChat.getUser().getId().equals(contextUser.getId())
                        && (userChat.getPermissionLevel().equals(UserChat.PermissionLevel.ADMIN)
                        || userChat.getPermissionLevel().equals(UserChat.PermissionLevel.OWNER)));
        if(!isContextUserAdminOrOwner) throw new NotEnoughPermissionLevelException();

        boolean isTargetUserAlreadyAdded = groupChatEntity.getUserChats().stream()
                .anyMatch(userChat -> userChat.getUser().getId().equals(targetUser.getId()));
        if(isTargetUserAlreadyAdded) throw new UserAlreadyMemberOfChatException();

        UserChat targetUserChat = UserChat.builder()
                .user(targetUser)
                .chat(groupChatEntity)
                .permissionLevel(UserChat.PermissionLevel.MEMBER)
                .build();
        userChatRepository.saveAndFlush(targetUserChat);
    }

    public void removeMemberFromChat(RemoveMemberFromGroupChatRequestDto requestDto) {
        GroupChatEntity groupChatEntity = groupChatRepository.findByIdWithFetch(requestDto.getChatId())
                .orElseThrow(ChatNotFoundException::new);

        UserEntity targetUser = userRepository.findById(requestDto.getTargetUserId())
                .orElseThrow(UserNotFoundException::new);

        UserEntity contextUser = JwtTokenService.getContextUser();

        UserChat targetUserChat = groupChatEntity.getUserChats().stream()
                .filter(groupChat -> groupChat.getUser().getId().equals(targetUser.getId()))
                .findAny()
                .orElseThrow(UserNotInChatException::new);

        //todo rewrite with permissions
        //check if user have enough PermissionLevel
        boolean isContextUserHavePermission = groupChatEntity.getUserChats().stream()
                .anyMatch(contextUserChat ->
                        contextUserChat.getUser().getId().equals(contextUser.getId()) &&
                                (targetUserChat.getPermissionLevel().equals(UserChat.PermissionLevel.MEMBER)
                                        && (contextUserChat.getPermissionLevel().equals(UserChat.PermissionLevel.ADMIN)
                                        || contextUserChat.getPermissionLevel().equals(UserChat.PermissionLevel.OWNER))
                                ||
                                (targetUserChat.getPermissionLevel().equals(UserChat.PermissionLevel.ADMIN)
                                        && contextUserChat.getPermissionLevel().equals(UserChat.PermissionLevel.MEMBER))
                                )
                );
        if(!isContextUserHavePermission) throw new NotEnoughPermissionLevelException();

        userChatRepository.delete(targetUserChat);
    }

    //just for test todo delete this (or no)
    public List<UserSearchInfoDto> getChatUsersList(UUID chatId) {
        GroupChatEntity groupChatEntity = groupChatRepository.findByIdWithFetch(chatId)
                .orElseThrow(ChatNotFoundException::new);

        UserEntity contextUser = JwtTokenService.getContextUser();

        boolean isContextUserInChat = groupChatEntity.getUserChats().stream()
                .anyMatch(userChat -> {
                    return userChat.getUser().getId().equals(contextUser.getId());
                });
        if(!isContextUserInChat) throw new UserNotMemberOfChatException();

        return groupChatEntity.getUserChats().stream()
                .map(userChat -> UserSearchInfoDto.from(userChat.getUser()))
                .collect(Collectors.toList());
    }
}
