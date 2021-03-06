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
        entityManager.merge(userEntity);
    }

    @Transactional
    public UserEntity getUserByLogin(String login) {
        return entityManager.find(UserEntity.class, login);
    }

    @Transactional
    public List<UserEntity> getAllUsers() {
        return entityManager.createQuery("FROM UserEntity", UserEntity.class).getResultList();
    }
}
