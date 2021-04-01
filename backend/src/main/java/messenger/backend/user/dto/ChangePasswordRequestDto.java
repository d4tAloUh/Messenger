package messenger.backend.user.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class ChangePasswordRequestDto {
    @NotBlank
    private String oldPassword;
    @NotBlank
    private String newPassword;
}
