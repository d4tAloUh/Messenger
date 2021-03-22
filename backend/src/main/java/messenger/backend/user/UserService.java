package messenger.backend.user;

import lombok.RequiredArgsConstructor;
import messenger.backend.user.dto.UserSearchInfoDto;
import messenger.backend.user.exceptions.UserNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserSearchInfoDto getUserSearchInfo(String username) {
        UserEntity userEntity = userRepository.getByUsername(username)
                .orElseThrow(UserNotFoundException::new);
        return UserSearchInfoDto.from(userEntity);
    }

    // TODO delete
    public List<UserEntity> getAllUsers() {
        return userRepository.findAll();
    }
}
