package messenger.backend.models;

import lombok.*;
import org.springframework.scheduling.support.SimpleTriggerContext;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "users")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class UserEntity {

    @Id
    @Column(name = "login")
    private String login;

    @Column(name = "password")
    private String password;
}
