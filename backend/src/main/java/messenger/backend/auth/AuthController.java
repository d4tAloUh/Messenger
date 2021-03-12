package messenger.backend.auth;

import lombok.RequiredArgsConstructor;
import messenger.backend.auth.dto.AuthRequestDto;
import messenger.backend.auth.dto.AuthResponseDto;
import messenger.backend.auth.dto.RefreshDto;
import messenger.backend.auth.refresh_token.RefreshTokenEntity;
import messenger.backend.auth.refresh_token.RefreshTokenRepository;
import messenger.backend.user.dto.UserDto;
import messenger.backend.utils.Response;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthController {

    private final RefreshTokenRepository refreshTokenRepository;
    private final AuthService            authService;

    @PostMapping("/login")
    public Response<AuthResponseDto> login(@Valid @RequestBody AuthRequestDto authRequestDto) {
            return Response.success(authService.authenticate(authRequestDto));
    }

    @PostMapping("/logout")
    public void logout(@Valid @RequestBody RefreshDto refreshDto) {
        authService.logout(refreshDto);
    }

    @GetMapping("/me")
    public Response<UserDto> getUserInfo(HttpServletRequest httpRequest) {
            return Response.success(authService.getUserInfo(httpRequest));
    }

    @PostMapping("/refresh")
    public Response<AuthResponseDto> refresh(@Valid @RequestBody RefreshDto refreshDto) {
            return Response.success(authService.refreshToken(refreshDto));
    }

    @GetMapping("/tokens") // just for test todo delete this
    public List<RefreshTokenEntity> getTokens() {
        return refreshTokenRepository.findAll();
    }

    @PostMapping("/logout/all") // just for test (or no) todo delete this?
    public void logoutFromAllDevices(HttpServletRequest httpRequest) {
        authService.logoutAll(httpRequest);
    }

}
