package messenger.backend.chat.group.dto;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class CreateGroupChatRequestDto {
    @NotEmpty
    private String chatName;
}
