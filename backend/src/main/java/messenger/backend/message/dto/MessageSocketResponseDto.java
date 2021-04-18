package messenger.backend.message.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class MessageSocketResponseDto {
    private UUID loadingId;
    private MessageResponseDto message;
}
