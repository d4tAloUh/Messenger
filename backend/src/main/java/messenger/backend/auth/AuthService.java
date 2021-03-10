package messenger.backend.auth;

import lombok.RequiredArgsConstructor;
import messenger.backend.auth.dto.AuthResponseDto;
import messenger.backend.auth.jwt.JwtTokenService;
import messenger.backend.auth.refresh_token.RefreshTokenService;
import messenger.backend.auth.security.SecurityUser;
import messenger.backend.user.UserEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenService        jwtTokenService;
    private final RefreshTokenService    refreshTokenService;


    public AuthResponseDto authenticate(String username, String password) {
        Authentication authentication;
        try {
            authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (BadCredentialsException e) {
            throw new BadCredentialsException("Invalid username or password");
        }
        UserEntity userEntity = ((SecurityUser) authentication.getPrincipal()).getUserEntity();
        return buildAuthResponse(userEntity);
    }

    public AuthResponseDto buildAuthResponse(UserEntity userEntity) {
        String accessToken = jwtTokenService.createToken(userEntity);
        String refreshToken = refreshTokenService.newToken(userEntity);

        AuthResponseDto responseDto = new AuthResponseDto();
        responseDto.setAccessToken(accessToken);
        responseDto.setRefreshToken(refreshToken);

        return responseDto;
    }

    public AuthResponseDto refreshToken(String refreshToken) {
        UserEntity      userEntity  = refreshTokenService.getUserByToken(refreshToken);
        return buildAuthResponse(userEntity);
    }
}
