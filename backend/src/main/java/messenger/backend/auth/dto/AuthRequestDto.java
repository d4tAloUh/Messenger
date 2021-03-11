package messenger.backend.auth.dto;

import lombok.Data;
import lombok.Value;

@Data
public class AuthRequestDto {
    private String username;
    private String password;
}
