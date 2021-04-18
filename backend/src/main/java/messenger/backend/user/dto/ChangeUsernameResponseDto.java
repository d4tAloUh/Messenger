package messenger.backend.user.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.UUID;

@AllArgsConstructor
@Data
public class ChangeUsernameResponseDto {
    private UUID userId;
    private String newUsername;
}
