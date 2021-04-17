package messenger.backend.user;

import lombok.*;
import messenger.backend.auth.access_levels.Role;
import messenger.backend.seeds.FakerService;
import messenger.backend.userChat.UserChat;
import org.apache.commons.lang3.ArrayUtils;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString

@Entity
@Table(name = "Users")
public class UserEntity {

    public static UserEntity generateUser() {

        return UserEntity.builder()
                .username(FakerService.faker.name().username().replace(".",""))
                .password("$2y$12$ixe4Lh4uQVncJDzPJWckfeyTXPMkuVZm55miqLdnn/TjH0FoF8HOq") // encrypted "user"
                .fullName(FakerService.faker.name().fullName())
                .bio(FakerService.faker.backToTheFuture().quote())
                .role(Role.USER)
                .build();
    }

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    @Column(name = "UserId")
    private UUID id;

    @Column(name = "Username", unique = true, length = 64, nullable = false)
    private String username;

    @Column(name = "Password", nullable = false)
    private String password;

    @ToString.Exclude
    @Column(name = "FullName", length = 128, nullable = false)
    private String fullName;

    @ToString.Exclude
    @Column(name = "Bio", length = 512)
    private String bio;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "role")
    private Role role;

    @ToString.Exclude
    @OneToMany(mappedBy = "user")
    private List<UserChat> userChats = new ArrayList<>();

}
