package messenger.backend.chat.group.dto;

import lombok.Data;
import messenger.backend.userChat.UserChat;

import java.util.UUID;


@Data
public class GroupChatUserInfoDto {
    private UUID id;
    private String username;
    private String fullName;
    private UserChat.PermissionLevel permissionLevel;
    private Byte[] profilePicture;
}
