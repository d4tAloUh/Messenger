package messenger.backend.repositories;

import lombok.RequiredArgsConstructor;
import messenger.backend.models.UserEntity;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class UserRepository {

    private final EntityManager entityManager;

    @Transactional
    public void createUser(UserEntity userEntity) {
        //todo throw exception when same login?
        entityManager.merge(userEntity);
    }

    @Transactional
    public UserEntity getUserByUsername(String username) {
        return entityManager.find(UserEntity.class, username);
    }

    @Transactional
    public List<UserEntity> getAllUsers() {
        return entityManager.createQuery("FROM UserEntity", UserEntity.class).getResultList();
    }
}
