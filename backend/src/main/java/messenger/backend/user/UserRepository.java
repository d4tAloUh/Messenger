package messenger.backend.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface UserRepository extends JpaRepository<UserEntity, String> {

    //todo throw exception when same username?

    UserEntity getUserByUsername(String username);
}
