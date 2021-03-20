package messenger.backend.chat.personal.dto;


import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class CreatePersonalChatRequestDto {
    @NotEmpty
    private String targetUsername;
}
