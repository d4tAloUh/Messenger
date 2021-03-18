package messenger.backend.entities;

import lombok.*;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
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
    private UUID userId;

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

    @OneToMany(mappedBy = "userIdFK")
    private List<MessageEntity> messageEntities = new ArrayList<>();
}