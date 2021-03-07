package messenger.backend.auth.refresh_token;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

@Repository
@RequiredArgsConstructor
public class RefreshTokenRepository {

    private final EntityManager entityManager;

    @Transactional
    public RefreshTokenEntity createToken(RefreshTokenEntity refreshTokenEntity) {
        return entityManager.merge(refreshTokenEntity);
    }

    @Transactional
    public void deleteUserRefreshToken(RefreshTokenEntity tokenEntity) {
        entityManager.remove(tokenEntity);
    }

    @Transactional
    public RefreshTokenEntity getTokenById(String id) {
        return entityManager.find(RefreshTokenEntity.class, id);
    }

    @Transactional
    public RefreshTokenEntity getTokenByUsername(String username) {
        return entityManager.createQuery(
                "SELECT r FROM RefreshTokenEntity as r INNER JOIN r.userEntity as u " +
                        "WHERE u.username = :currentUsername", RefreshTokenEntity.class)
                .setParameter("currentUsername", username)
                .getSingleResult();
    }
}
