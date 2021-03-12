package messenger.backend.auth.dto;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class RefreshDto {
    @NotEmpty
    private String refreshToken;
}
