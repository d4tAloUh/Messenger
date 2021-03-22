package messenger.backend.chat.group;

import lombok.RequiredArgsConstructor;
import messenger.backend.auth.jwt.JwtTokenService;
import messenger.backend.chat.GroupChatEntity;
import messenger.backend.chat.exceptions.ChatNotFoundException;
import messenger.backend.chat.group.dto.CreateGroupChatRequestDto;
import messenger.backend.chat.group.dto.CreateGroupChatResponseDto;
import messenger.backend.chat.group.dto.DeleteGroupChatRequestDto;
import messenger.backend.chat.group.exceptions.UserNotOwnerOfChatException;
import messenger.backend.user.UserEntity;
import messenger.backend.userChat.UserChat;
import messenger.backend.userChat.UserChatRepository;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor

@Service
public class GroupChatService {

    private final GroupChatRepository groupChatRepository;
    private final UserChatRepository userChatRepository;


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

}
