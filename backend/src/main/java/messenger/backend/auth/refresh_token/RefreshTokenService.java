package messenger.backend.auth.refresh_token;

import lombok.RequiredArgsConstructor;
import messenger.backend.user.UserEntity;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@RequiredArgsConstructor
public class RefreshTokenService {

    private final RefreshTokenRepository refreshTokenRepository;

    @Value("${refresh.validity}")
    private long validityInMilliseconds;

    public RefreshTokenEntity createToken(UserEntity userEntity) {
        try {
            refreshTokenRepository
                    .deleteUserRefreshToken(refreshTokenRepository.getTokenByUsername(userEntity.getUsername()));
        } catch (Exception e) {
            //user didn't have refreshToken before
        }

        return refreshTokenRepository.createToken(
                RefreshTokenEntity.builder()
                                    .createdAt(new Date().getTime())
                                    .userEntity(userEntity)
                                    .build());
    }

    public boolean validateToken(String token) {
        RefreshTokenEntity refreshTokenEntity = refreshTokenRepository.getTokenById(token);
        return validateToken(refreshTokenEntity);
    }

    public boolean validateToken(RefreshTokenEntity refreshTokenEntity) {
        return refreshTokenEntity != null &&
                refreshTokenEntity.getCreatedAt() + validityInMilliseconds < new Date().getTime();
    }
}
