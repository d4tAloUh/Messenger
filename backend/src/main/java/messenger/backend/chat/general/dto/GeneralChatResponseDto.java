package messenger.backend.chat.general.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import messenger.backend.chat.ChatSuperclass;
import messenger.backend.chat.GroupChatEntity;
import messenger.backend.chat.PrivateChatEntity;
import messenger.backend.user.UserEntity;
import messenger.backend.userChat.UserChat;

import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class GeneralChatResponseDto {

    public static GeneralChatResponseDto fromEntity(ChatSuperclass chat, UUID currentUserId) {
        if (chat instanceof GroupChatEntity) {
            return fromGroupEntity((GroupChatEntity) chat);
        } else if (chat instanceof PrivateChatEntity) {
            return fromPrivateEntity((PrivateChatEntity) chat, currentUserId);
        } else {
            throw new RuntimeException();
        }
    }

    public static GeneralChatResponseDto fromGroupEntity(GroupChatEntity chat) {
        return GeneralChatResponseDto.builder()
                .id(chat.getId())
                .type("GROUP")
                .title(chat.getGroupName())
                .build();
    }

    public static GeneralChatResponseDto fromPrivateEntity(PrivateChatEntity chat, UUID currentUserId) {
        var companionName = chat.getUserChats()
                .stream()
                .filter(uc -> !uc.getUser().getId().equals(currentUserId))
                .findFirst()
                .map(UserChat::getUser)
                .map(UserEntity::getFullName)
                .orElseThrow(RuntimeException::new);
        return GeneralChatResponseDto.builder()
                .id(chat.getId())
                .type("PERSONAL")
                .title(companionName)
                .build();
    }

    private UUID id;
    private String title;
    private String type;
}
