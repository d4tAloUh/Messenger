package messenger.backend;

import lombok.RequiredArgsConstructor;
import messenger.backend.auth.access_levels.Role;
import messenger.backend.user.UserEntity;
import messenger.backend.user.UserRepository;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor
public class DbInitializer {

    private final UserRepository userRepository;

    @EventListener
    public void onApplicationEvent(ContextRefreshedEvent event) {
        userRepository.saveAndFlush(
                UserEntity.builder()
                        .username("user")
                        .fullName("userFullName")
                        .password("$2y$12$ixe4Lh4uQVncJDzPJWckfeyTXPMkuVZm55miqLdnn/TjH0FoF8HOq") //user (BCryptPasswordEncoder(12))
                        .role(Role.USER)
                        .build());
    }
}
