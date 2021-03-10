package messenger.backend.auth.security;

import lombok.RequiredArgsConstructor;
import messenger.backend.user.UserEntity;
import messenger.backend.user.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SecurityUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity userEntity = userRepository.getUserByUsername(username);
        //todo exception if user not found
        return new SecurityUser(userEntity);
    }
}
