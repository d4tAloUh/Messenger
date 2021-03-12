package messenger.backend.auth;

import lombok.RequiredArgsConstructor;
import messenger.backend.auth.dto.AuthRequestDto;
import messenger.backend.auth.dto.AuthResponseDto;
import messenger.backend.auth.dto.RefreshTokenDto;
import messenger.backend.auth.exceptions.InvalidUsernameOrPasswordException;
import messenger.backend.auth.jwt.JwtTokenService;
import messenger.backend.refreshToken.RefreshTokenEntity;
import messenger.backend.refreshToken.RefreshTokenRepository;
import messenger.backend.auth.security.SecurityUser;
import messenger.backend.refreshToken.exceptions.RefreshTokenInvalidException;
import messenger.backend.user.UserEntity;
import messenger.backend.user.UserRepository;
import messenger.backend.user.dto.CurrentUserInfoDto;
import messenger.backend.user.exceptions.UserNotFoundException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenService jwtTokenService;
    private final UserRepository userRepository;
    private final RefreshTokenRepository refreshTokenRepository;

    @Value("${refresh.validity}")
    private long refreshTokenValidityInMilliseconds;

    public AuthResponseDto login(AuthRequestDto authRequestDto) {
        try {
            var username = authRequestDto.getUsername();
            var password = authRequestDto.getPassword();
            var authentication = authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(username, password));

            var userEntity = ((SecurityUser) authentication.getPrincipal()).getUserEntity();
            return buildAuthResponse(userEntity);
        } catch (BadCredentialsException e) {
            throw new InvalidUsernameOrPasswordException();
        }
    }

    private AuthResponseDto buildAuthResponse(UserEntity userEntity) {
        var refreshTokenEntity = RefreshTokenEntity.fromUserEntity(userEntity);
        refreshTokenRepository.save(refreshTokenEntity);

        var refreshToken = refreshTokenEntity.getId();
        var accessToken = jwtTokenService.createToken(userEntity);

        return AuthResponseDto.of(accessToken, refreshToken);
    }

    public AuthResponseDto refreshToken(RefreshTokenDto refreshTokenDto) {
        var refreshToken = refreshTokenDto.getRefreshToken();
        var refreshTokenEntity = refreshTokenRepository
                .findById(refreshToken)
                .filter(this::validateRefreshToken)
                .orElseThrow(RefreshTokenInvalidException::new);
        var userEntity = refreshTokenEntity.getUserEntity();
        return buildAuthResponse(userEntity);
    }

    public void logout(RefreshTokenDto refreshTokenDto) {
        var refreshToken = refreshTokenDto.getRefreshToken();
        refreshTokenRepository.deleteById(refreshToken);
    }

    public CurrentUserInfoDto getCurrentUserInfo(HttpServletRequest httpRequest) {
        var jwtPayload = jwtTokenService.getJwtPayload(httpRequest);
        var userEntity = userRepository
                .findById(jwtPayload.getId())
                .orElseThrow(UserNotFoundException::new);
        return CurrentUserInfoDto.from(userEntity);
    }

    public void logoutAll(HttpServletRequest httpRequest) {
        var userId = jwtTokenService.getUserId(httpRequest);
        refreshTokenRepository.deleteAllByUserEntityId(userId);
    }

    public boolean validateRefreshToken(RefreshTokenEntity refreshTokenEntity) {
        return refreshTokenEntity.getCreatedAt() + refreshTokenValidityInMilliseconds > new Date().getTime();
    }
}
