package messenger.backend.refreshToken;

import lombok.*;
import messenger.backend.user.UserEntity;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "refreshToken")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class RefreshTokenEntity {

    public static RefreshTokenEntity fromUserEntity(UserEntity userEntity) {
        return RefreshTokenEntity.builder()
                .userEntity(userEntity)
                .build();
    }

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    @Column(name = "id")
    private UUID id;

    @Column(name = "createdAt")
    private Long createdAt;

    @OneToOne
    @JoinColumn(name = "userId")
    private UserEntity userEntity;
}
