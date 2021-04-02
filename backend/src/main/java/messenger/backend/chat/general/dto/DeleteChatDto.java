package messenger.backend.chat.general.dto;

import lombok.Value;

import java.util.UUID;

@Value(staticConstructor = "of")
public class DeleteChatDto {
    UUID chatId;
}
