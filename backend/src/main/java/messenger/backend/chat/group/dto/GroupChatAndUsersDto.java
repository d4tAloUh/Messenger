package messenger.backend.chat.group.dto;

import lombok.Builder;
import lombok.Data;
import messenger.backend.chat.GroupChatEntity;
import messenger.backend.user.UserEntity;

@Data
@Builder
public class GroupChatAndUsersDto {
    private GroupChatEntity groupChatEntity;
    private UserEntity contextUserEntity;
    private UserEntity targetUserEntity;
}
