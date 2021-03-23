package messenger.backend.chat.group;

import lombok.RequiredArgsConstructor;
import messenger.backend.auth.jwt.JwtTokenService;
import messenger.backend.chat.GroupChatEntity;
import messenger.backend.chat.exceptions.*;
import messenger.backend.chat.group.dto.*;
import messenger.backend.chat.group.exceptions.NotEnoughPermissionLevelException;
import messenger.backend.chat.group.exceptions.UserNotOwnerOfChatException;
import messenger.backend.user.UserEntity;
import messenger.backend.user.UserRepository;
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
        GroupChatAndUsersDto chatAndUsersDto = resolveEntities(requestDto.getChatId(), requestDto.getTargetUserId());
        GroupChatEntity groupChatEntity = chatAndUsersDto.getGroupChatEntity();
        UserEntity contextUser = chatAndUsersDto.getContextUserEntity();
        UserEntity targetUser = chatAndUsersDto.getTargetUserEntity();

        UserChat contextUserChat = groupChatEntity.getUserChats().stream()
                .filter(userChat -> userChat.getUser().getId().equals(contextUser.getId()))
                .findAny()
                .orElseThrow(ContextUserNotMemberOfChatException::new);

        if (contextUserChat.getPermissionLevel().equals(UserChat.PermissionLevel.MEMBER))
            throw new NotEnoughPermissionLevelException();

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
        UserChatsDto userChatsDto = resolveEntities(resolveEntities(requestDto.getChatId(), requestDto.getTargetUserId()));
        UserChat contextUserChat = userChatsDto.getContextUserChatEntity();
        UserChat targetUserChat = userChatsDto.getTargetUserChatEntity();

        //todo rewrite with permissions
        if (contextUserChat.getPermissionLevel().equals(UserChat.PermissionLevel.MEMBER))
            throw new NotEnoughPermissionLevelException();

        userChatRepository.delete(targetUserChat);
    }

    //just for test todo delete this (or no)
    public List<GroupChatUserInfoDto> getChatUsersList(UUID chatId) {
        GroupChatEntity groupChatEntity = groupChatRepository.findByIdWithFetch(chatId)
                .orElseThrow(ChatNotFoundException::new);

        UserEntity contextUser = JwtTokenService.getContextUser();

        boolean isContextUserInChat = groupChatEntity.getUserChats().stream()
                .anyMatch(userChat -> userChat.getUser().getId().equals(contextUser.getId()));
        if(!isContextUserInChat) throw new UserNotMemberOfChatException();

        return groupChatEntity.getUserChats().stream()
                .map(userChat -> {
                    GroupChatUserInfoDto infoDto = new GroupChatUserInfoDto();
                    infoDto.setId(userChat.getUser().getId());
                    infoDto.setUsername(userChat.getUser().getUsername());
                    infoDto.setFullName(userChat.getUser().getFullName());
                    infoDto.setPermissionLevel(userChat.getPermissionLevel());
                    infoDto.setProfilePicture(userChat.getUser().getProfilePicture());
                    return infoDto;
                })
                .collect(Collectors.toList());
    }

    public void upgradeToAdmin(UpgradeToAdminRequestDto requestDto) {
        UserChatsDto userChatsDto = resolveEntities(resolveEntities(requestDto.getChatId(), requestDto.getTargetUserId()));
        UserChat contextUserChat = userChatsDto.getContextUserChatEntity();
        UserChat targetUserChat = userChatsDto.getTargetUserChatEntity();

        if(!contextUserChat.getPermissionLevel().equals(UserChat.PermissionLevel.OWNER))
            throw new NotEnoughPermissionLevelException();

        if (targetUserChat.getPermissionLevel().equals(UserChat.PermissionLevel.MEMBER)) {
            targetUserChat.setPermissionLevel(UserChat.PermissionLevel.ADMIN);
        } else {
            throw new InvalidChatOperationException();
        }

        userChatRepository.saveAndFlush(targetUserChat);
    }

    public void downgradeToMember(DowngradeToMemberRequestDto requestDto) {
        UserChatsDto userChatsDto = resolveEntities(resolveEntities(requestDto.getChatId(), requestDto.getTargetUserId()));
        UserChat contextUserChat = userChatsDto.getContextUserChatEntity();
        UserChat targetUserChat = userChatsDto.getTargetUserChatEntity();

        if(!contextUserChat.getPermissionLevel().equals(UserChat.PermissionLevel.OWNER))
            throw new NotEnoughPermissionLevelException();

        if (targetUserChat.getPermissionLevel().equals(UserChat.PermissionLevel.ADMIN)) {
            targetUserChat.setPermissionLevel(UserChat.PermissionLevel.MEMBER);
        } else {
            throw new InvalidChatOperationException();
        }

        userChatRepository.saveAndFlush(targetUserChat);
    }

    private GroupChatAndUsersDto resolveEntities(UUID chatId, UUID targetUserId) {
        GroupChatEntity groupChatEntity = groupChatRepository.findByIdWithFetch(chatId)
                .orElseThrow(ChatNotFoundException::new);

        UserEntity targetUserEntity = userRepository.findById(targetUserId)
                .orElseThrow(UserNotFoundException::new);

        UserEntity contextUserEntity = JwtTokenService.getContextUser();

        return GroupChatAndUsersDto.builder()
                .groupChatEntity(groupChatEntity)
                .contextUserEntity(contextUserEntity)
                .targetUserEntity(targetUserEntity)
                .build();
    }

    private UserChatsDto resolveEntities(GroupChatEntity groupChatEntity, UUID contextUserId, UUID targetUserId) {
        UserChat contextUserChatEntity = groupChatEntity.getUserChats().stream()
                .filter(userChat -> userChat.getUser().getId().equals(contextUserId))
                .findAny()
                .orElseThrow(ContextUserNotMemberOfChatException::new);

        UserChat targetUserChatEntity = groupChatEntity.getUserChats().stream()
                .filter(groupChat -> groupChat.getUser().getId().equals(targetUserId))
                .findAny()
                .orElseThrow(TargetUserNotMemeberOfChatException::new);

        return UserChatsDto.builder()
                .contextUserChatEntity(contextUserChatEntity)
                .targetUserChatEntity(targetUserChatEntity)
                .build();
    }

    private UserChatsDto resolveEntities(GroupChatAndUsersDto chatAndUsersDto) {
        return resolveEntities(
                chatAndUsersDto.getGroupChatEntity(),
                chatAndUsersDto.getContextUserEntity().getId(),
                chatAndUsersDto.getTargetUserEntity().getId()
        );
    }

}
