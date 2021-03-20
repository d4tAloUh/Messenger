package messenger.backend.chat.personal.dto;

import lombok.Data;

@Data
public class DeletePersonalChatRequestDto {
    //todo bad request with UUID type
    private String chatId;
}
