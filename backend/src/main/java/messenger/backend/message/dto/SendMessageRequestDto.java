package messenger.backend.message.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SendMessageRequestDto {
    @NotNull
    @Size(min = 1)
    private String text;
    @NotNull
    private UUID chatId;
    @NotNull
    private UUID loadingId;
}
