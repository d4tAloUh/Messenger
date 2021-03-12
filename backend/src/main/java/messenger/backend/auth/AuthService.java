package messenger.backend.auth;

import lombok.RequiredArgsConstructor;
import messenger.backend.auth.dto.AuthRequestDto;
import messenger.backend.auth.dto.AuthResponseDto;
import messenger.backend.auth.exceptions.InvalidUsernameOrPasswordException;
import messenger.backend.auth.jwt.JwtTokenService;
import messenger.backend.auth.refresh_token.RefreshTokenService;
import messenger.backend.auth.security.SecurityUser;
import messenger.backend.user.UserEntity;
import messenger.backend.user.dto.UserDto;
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


    public AuthResponseDto authenticate(AuthRequestDto authRequestDto) {
        String username = authRequestDto.getUsername();
        String password = authRequestDto.getPassword();
        Authentication authentication;
        try {
            authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (BadCredentialsException e) {
            throw new InvalidUsernameOrPasswordException();
        }
        UserEntity userEntity = ((SecurityUser) authentication.getPrincipal()).getUserEntity();
        return buildAuthResponse(UserDto.from(userEntity));
    }

    public AuthResponseDto buildAuthResponse(UserDto userDto) {
        String accessToken = jwtTokenService.createToken(userDto);
        String refreshToken = refreshTokenService.newToken(userDto);

        return AuthResponseDto.of(accessToken, refreshToken);
    }

    public AuthResponseDto refreshToken(String refreshToken) {
        UserEntity userEntity = refreshTokenService.getUserEntityByToken(refreshToken);
        return buildAuthResponse(UserDto.from(userEntity));
    }
}
