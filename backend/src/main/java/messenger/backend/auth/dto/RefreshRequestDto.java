package messenger.backend.auth.dto;

import lombok.Data;
import lombok.Value;

@Data
public class RefreshRequestDto {
    private String refreshToken;
}
