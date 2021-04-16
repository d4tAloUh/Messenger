package messenger.backend.auth.dto;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Data
public class RegisterRequestDto {
    @NotEmpty
    @Size(min = 4, max = 16)
    private String username;
    @NotEmpty
    @Size(min = 4, max = 32)
    private String fullName;
    @NotEmpty
    @Size(min = 4, max = 16)
    private String password;
}
