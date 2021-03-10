package messenger.backend.auth.refresh_token;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshTokenEntity, String> {

    @Query("SELECT rt FROM RefreshTokenEntity rt JOIN rt.userEntity u WHERE u.id=:userId")
    List<RefreshTokenEntity> findByUserId(@Param("userId")String userId);

    Optional<RefreshTokenEntity> findTokenById(String id);
}
