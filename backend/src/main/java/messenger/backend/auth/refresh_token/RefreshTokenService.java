package messenger.backend.auth.refresh_token;

import lombok.RequiredArgsConstructor;
import messenger.backend.user.UserEntity;
import messenger.backend.utils.exceptions.ValidationException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RefreshTokenService {

    private final RefreshTokenRepository refreshTokenRepository;

    @Value("${refresh.validity}")
    private long validityInMilliseconds;

    public String newToken(UserEntity userEntity) {
        deleteUserTokens(userEntity.getId());
        return createAndSaveToken(userEntity);
    }

    public String createAndSaveToken(UserEntity userEntity) {
        return refreshTokenRepository.saveAndFlush(
                RefreshTokenEntity.builder()
                                    .createdAt(new Date().getTime())
                                    .userEntity(userEntity)
                                    .build())
                .getId();
    }

    public UserEntity getUserByToken(String token) {
        return getTokenEntity(token).getUserEntity();
    }

    public RefreshTokenEntity getTokenEntity(String token) {
        Optional<RefreshTokenEntity> refreshTokenOptional = refreshTokenRepository.findTokenById(token);

        if(refreshTokenOptional.isEmpty() || !validateToken(refreshTokenOptional.get())) {
            throw new ValidationException("Refresh token is expired or invalid");
        }

        return refreshTokenOptional.get();
    }

    public boolean validateToken(String token) {
        Optional<RefreshTokenEntity> refreshTokenOptional = refreshTokenRepository.findTokenById(token);
        if(refreshTokenOptional.isEmpty()){
            return false;
        }
        return validateToken(refreshTokenOptional.get());
    }

    public boolean validateToken(RefreshTokenEntity refreshTokenEntity) {
        return refreshTokenEntity.getCreatedAt() + validityInMilliseconds > new Date().getTime();
    }

    public void deleteUserTokens(String userId) {
        //todo delete in one query
        refreshTokenRepository.deleteAll(refreshTokenRepository.findByUserId(userId));
    }
}
