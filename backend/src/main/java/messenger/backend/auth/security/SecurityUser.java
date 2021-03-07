package messenger.backend.auth.security;

import lombok.Data;
import messenger.backend.models.UserEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Data
public class SecurityUser implements UserDetails {

    private final String                       username;
    private final String                       password;
    private final List<SimpleGrantedAuthority> authorities;
    private final boolean                      ACTIVE_ACCOUNT = true;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return ACTIVE_ACCOUNT;
    }

    @Override
    public boolean isAccountNonLocked() {
        return ACTIVE_ACCOUNT;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return ACTIVE_ACCOUNT;
    }

    @Override
    public boolean isEnabled() {
        return ACTIVE_ACCOUNT;
    }

    public static UserDetails fromUserEntity(UserEntity userEntity) {
        return new User(userEntity.getUsername(), userEntity.getPassword(), userEntity.getRole().getAuthorities());
//        return new SecurityUser(userEntity.getUsername(), userEntity.getPassword());
//        return new User(userEntity.getUsername(), userEntity.getPassword(), new ArrayList<>());
    }
}
