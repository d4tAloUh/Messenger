package messenger.backend.auth;

import lombok.RequiredArgsConstructor;
import messenger.backend.auth.dto.AuthRequestDto;
import messenger.backend.auth.dto.AuthResponseDto;
import messenger.backend.auth.dto.RefreshRequestDto;
import messenger.backend.auth.refresh_token.RefreshTokenEntity;
import messenger.backend.auth.refresh_token.RefreshTokenRepository;
import messenger.backend.user.dto.UserDto;
import messenger.backend.utils.Response;
import org.springframework.http.ResponseEntity;
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
    public void logout(HttpServletRequest request) {
        authService.logout(request);
    }

    @GetMapping("/me")
    public Response<UserDto> getUserInfo(HttpServletRequest httpRequest) {
            return Response.success(authService.getUserInfo(httpRequest));
    }

    @PostMapping("/refresh")
    public Response<AuthResponseDto> refresh(@Valid  @RequestBody RefreshRequestDto refreshRequestDto) {
            AuthResponseDto authResponseDto = authService.refreshToken(refreshRequestDto);
            return Response.success(authResponseDto);
    }

    @GetMapping("/tokens") // just for test todo delete this
    public List<RefreshTokenEntity> getTokens() {
        return refreshTokenRepository.findAll();
    }

}
