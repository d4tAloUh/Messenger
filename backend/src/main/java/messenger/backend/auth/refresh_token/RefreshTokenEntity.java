package messenger.backend.auth.refresh_token;

import lombok.*;
import messenger.backend.user.UserEntity;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Table(name = "refreshToken")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class RefreshTokenEntity {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    @Column(name = "id")
    private String id;

    @Column(name = "createdAt")
    private Long createdAt;

    @OneToOne
    @JoinColumn(name = "userId")
    private UserEntity userEntity;
}
