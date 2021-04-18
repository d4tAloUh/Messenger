package messenger.backend.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
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
