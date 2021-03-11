package messenger.backend.auth;

import lombok.RequiredArgsConstructor;
import messenger.backend.auth.dto.AuthRequestDto;
import messenger.backend.auth.dto.AuthResponseDto;
import messenger.backend.auth.dto.RefreshRequestDto;
import messenger.backend.user.dto.UserDto;
import messenger.backend.auth.jwt.JwtTokenService;
import messenger.backend.auth.refresh_token.RefreshTokenEntity;
import messenger.backend.auth.refresh_token.RefreshTokenRepository;
import messenger.backend.auth.refresh_token.RefreshTokenService;
import messenger.backend.user.UserEntity;
import messenger.backend.user.UserRepository;
import messenger.backend.utils.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthController {

    private final JwtTokenService        jwtTokenService;
    private final RefreshTokenRepository refreshTokenRepository;
    private final AuthService            authService;
    private final RefreshTokenService    refreshTokenService;

    @PostMapping("/login")
    public ResponseEntity<Response<AuthResponseDto>> login(@RequestBody AuthRequestDto authRequestDto) {
        try {
            String username = authRequestDto.getUsername();
            String password = authRequestDto.getPassword();
            return ResponseEntity.ok(Response.data(authService.authenticate(username, password)));

        } catch (Exception e) {
            return ResponseEntity
                    .badRequest()
                    .body(Response.error(e.getMessage()));
        }
    }

    @PostMapping("/logout")
    public void logout(HttpServletRequest request) {
        refreshTokenService.deleteUserTokens(jwtTokenService.getUserId(request));
    }

    @GetMapping("/me")
    public ResponseEntity<Response<UserDto>> getUserInfo(HttpServletRequest httpRequest) {
        try {
            return ResponseEntity.ok(Response.data(jwtTokenService.getUserDto(httpRequest)));

        } catch (Exception e) {
            return ResponseEntity
                    .badRequest()
                    .body(Response.error(e.getMessage()));
        }

    }

    @PostMapping("/refresh")
    public ResponseEntity<Response<AuthResponseDto>> refresh(@RequestBody RefreshRequestDto refreshRequestDto) {
        try {
            AuthResponseDto authResponseDto = authService.refreshToken(refreshRequestDto.getRefreshToken());
            return ResponseEntity.ok(Response.data(authResponseDto));

        } catch (Exception e) {
            return ResponseEntity
                    .badRequest()
                    .body(Response.error(e.getMessage()));
        }
    }

    @GetMapping("/tokens") // just for test todo delete this
    public List<RefreshTokenEntity> getTokens() {
        return refreshTokenRepository.findAll();
    }

}
