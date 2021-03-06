package messenger.backend;

import lombok.RequiredArgsConstructor;

import messenger.backend.models.UserEntity;
import messenger.backend.repositories.UserRepository;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor
public class DbInitializer {

    private final UserRepository userRepository;

    @EventListener
    public void onApplicationEvent(ContextRefreshedEvent event) {
        userRepository.createUser(UserEntity.builder().login("admin").password("admin").build());
    }
}
