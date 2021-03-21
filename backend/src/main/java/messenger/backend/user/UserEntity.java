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
@Table(name = "User")
public class UserEntity {

    public static UserEntity generateUser() {

        return UserEntity.builder()
                .username(FakerService.faker.name().username())
                .password(FakerService.faker.internet().password(3,6))
                .fullName(FakerService.faker.name().fullName())
                .bio(FakerService.faker.backToTheFuture().quote())
                .status(UserStatus.OFFLINE)
                .profilePicture(ArrayUtils.toObject(FakerService.faker.internet().avatar().getBytes(StandardCharsets.UTF_8)))
                .build();
    }

    public enum UserStatus {
        ONLINE,
        OFFLINE,
    }

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    @Column(name = "UserId")
    private UUID id;

    @Column(name = "Username", unique = true, length = 64)
    private String username;

    @Column(name = "Password")
    private String password;

    @Column(name = "FullName", length = 128)
    private String fullName;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "Status")
    private UserStatus status;

    @Column(name = "Bio", length = 512)
    private String bio;

    @Lob
    @Column(name = "ProfilePicture")
    private Byte[] profilePicture;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "role")
    private Role role;

    @OneToMany(mappedBy = "user")
    private List<UserChat> userChats = new ArrayList<>();

}
