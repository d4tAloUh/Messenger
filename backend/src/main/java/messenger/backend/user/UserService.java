package messenger.backend.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    // TODO delete
    public List<UserEntity> getAllUsers() {
        return userRepository.findAll();
    }
}
