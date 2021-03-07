package messenger.backend.auth;

import lombok.RequiredArgsConstructor;
import messenger.backend.auth.dto.AuthResponseDto;
import messenger.backend.auth.jwt.JwtTokenService;
import messenger.backend.auth.refresh_token.RefreshTokenEntity;
import messenger.backend.auth.refresh_token.RefreshTokenService;
import messenger.backend.user.UserEntity;
import messenger.backend.user.UserRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final UserRepository         userRepository;
    private final JwtTokenService        jwtTokenService;
    private final RefreshTokenService    refreshTokenService;


    public AuthResponseDto authenticate(String username, String password) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        UserEntity userEntity = userRepository.getUserByUsername(username);
        if(userEntity == null) {
            throw new UsernameNotFoundException("User doesn't exist");
        }
        return buildAuthResponse(userEntity);
    }

    public AuthResponseDto buildAuthResponse(UserEntity userEntity) {
        String accessToken = jwtTokenService.createToken(userEntity.getUsername(), userEntity.getRole().name());
        RefreshTokenEntity refreshTokenEntity = refreshTokenService.createToken(userEntity);

        AuthResponseDto responseDto = new AuthResponseDto();
        responseDto.setAccessToken(accessToken);
        responseDto.setRefreshToken(refreshTokenEntity.getId());

        return responseDto;
    }

}
