package messenger.backend.chat.personal.dto;


import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class CreatePersonalChatRequestDto {
    @NotBlank
    private String targetUsername;
}
