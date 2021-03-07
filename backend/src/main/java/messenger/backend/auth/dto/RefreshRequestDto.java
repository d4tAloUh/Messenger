package messenger.backend.auth.dto;

import lombok.Data;

@Data
public class RefreshRequestDto {
    private String refreshToken;
}
