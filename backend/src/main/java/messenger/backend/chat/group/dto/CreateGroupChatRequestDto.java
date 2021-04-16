package messenger.backend.chat.group.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
public class CreateGroupChatRequestDto {
    @NotBlank
    @Size(min = 4, max = 32)
    private String chatName;
}
