package messenger.backend.user;

import lombok.RequiredArgsConstructor;
import messenger.backend.models.UserEntity;
import messenger.backend.repositories.UserRepository;
import org.springframework.context.annotation.Role;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class UserController {

    private final UserRepository userRepository;

    @GetMapping("/user/all")
    @PreAuthorize("hasAuthority(T(messenger.backend.auth.access_levels.Permission).USER_PERMISSION.getPermission())")
    public List<UserEntity> getAllUsers() {
        return userRepository.getAllUsers();
    }

}
