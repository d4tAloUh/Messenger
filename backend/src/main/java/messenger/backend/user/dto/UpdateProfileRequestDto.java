package messenger.backend.user.dto;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class UpdateProfileRequestDto {
    @NotNull
    @Size(min = 4, max = 32)
    private String fullName;
    @NotNull
    private String bio;
}
