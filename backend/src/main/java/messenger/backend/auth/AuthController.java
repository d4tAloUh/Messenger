package messenger.backend.auth;

import lombok.RequiredArgsConstructor;
import messenger.backend.auth.dto.AuthRequestDto;
import messenger.backend.auth.dto.RefreshRequestDto;
import messenger.backend.auth.dto.UserResponseDto;
import messenger.backend.auth.jwt.JwtTokenService;
import messenger.backend.auth.refresh_token.RefreshTokenEntity;
import messenger.backend.auth.refresh_token.RefreshTokenRepository;
import messenger.backend.auth.refresh_token.RefreshTokenService;
import messenger.backend.user.UserEntity;
import messenger.backend.user.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;

import javax.persistence.NoResultException;
import javax.servlet.http.HttpServletRequest;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthController {

    private final UserRepository         userRepository;
    private final JwtTokenService        jwtTokenService;
    private final RefreshTokenRepository refreshTokenRepository;
    private final AuthService            authService;
    private final RefreshTokenService    refreshTokenService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthRequestDto authRequestDto) {
        try {
            String username = authRequestDto.getUsername();
            String password = authRequestDto.getPassword();
            return ResponseEntity.ok(authService.authenticate(username, password));

        } catch (AuthenticationException e) {
            return new ResponseEntity<>("Invalid username or password", HttpStatus.FORBIDDEN);
        }
    }

    @PostMapping("/logout")
    public void logout(HttpServletRequest request) {
        //todo handle errors
        try {
            RefreshTokenEntity refreshTokenEntity =
                    refreshTokenRepository.getTokenByUsername(jwtTokenService.getUsername(request));
            refreshTokenRepository.deleteUserRefreshToken(refreshTokenEntity);
        } catch (Exception e) {
            //user already logout
        }
    }

    @GetMapping("/me")
    public UserResponseDto getMe(HttpServletRequest httpRequest) {
        //todo exceptions when user not found, etc
        String username = jwtTokenService.getUsername(httpRequest);
        UserEntity userEntity = userRepository.getUserByUsername(username);
        return UserResponseDto.from(userEntity);
    }

    @PostMapping("/refresh")
    public ResponseEntity<?> refresh(@RequestBody RefreshRequestDto refreshRequestDto) {
        RefreshTokenEntity refreshToken = refreshTokenRepository.getTokenById(refreshRequestDto.getRefreshToken());
        if(refreshTokenService.validateToken(refreshToken)) {
            return new ResponseEntity<>("RefreshToken expired or invalid", HttpStatus.FORBIDDEN);
        }
        UserEntity userEntity = refreshToken.getUserEntity();
        return ResponseEntity.ok(authService.buildAuthResponse(userEntity));
    }

}
