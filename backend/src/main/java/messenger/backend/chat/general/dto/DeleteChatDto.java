package messenger.backend.chat.general.dto;

import lombok.Value;

import javax.validation.constraints.NotNull;
import java.util.UUID;

@Value(staticConstructor = "of")
public class DeleteChatDto {
    @NotNull
    UUID chatId;
}
