package messenger.backend.user;

import lombok.RequiredArgsConstructor;
import messenger.backend.user.dto.UserSearchInfoDto;
import messenger.backend.utils.Response;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    @GetMapping("/search") //todo @NotEmpty validation
    public Response<UserSearchInfoDto> getUserSearchInfo(@RequestParam(name = "username") String username) {
        return Response.success(userService.getUserSearchInfo(username));
    }

    @GetMapping("/all") //todo delete this
    public List<UserEntity> getAllUsers() {
        return userService.getAllUsers();
    }

}
