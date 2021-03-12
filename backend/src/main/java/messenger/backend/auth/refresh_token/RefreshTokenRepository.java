package messenger.backend.auth.refresh_token;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.Optional;


@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshTokenEntity, String> {

    @Transactional
    void deleteAllByUserEntityId(String userId);

    @Modifying
    @Query("DELETE FROM RefreshTokenEntity WHERE id = :tokenId")
    void deleteById(@Param("tokenId") String tokenId);

    Optional<RefreshTokenEntity> findTokenById(String id);
}
