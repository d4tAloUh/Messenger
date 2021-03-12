package messenger.backend.auth.dto;

import lombok.Value;

@Value(staticConstructor = "of")
public class AuthResponseDto {
    String accessToken;
    String refreshToken;
}
