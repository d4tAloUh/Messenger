package messenger.backend.auth.dto;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.util.UUID;

@Data
public class RefreshTokenDto {
    @NotEmpty
    private UUID refreshToken;
}
