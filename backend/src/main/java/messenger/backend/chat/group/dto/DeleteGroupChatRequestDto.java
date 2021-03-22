package messenger.backend.chat.group.dto;


import lombok.Data;

import java.util.UUID;

@Data
public class DeleteGroupChatRequestDto {
    private UUID chatId;
}
