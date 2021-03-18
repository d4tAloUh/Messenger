package messenger.backend.entities;


import lombok.*;
import org.apache.catalina.User;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.UUID;


@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString

@Entity
@Table(name = "Message")
public class MessageEntity {
    public enum MessageType {
        TEXT,
        IMAGE,
    }

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    @Column(name = "MessageId")
    private UUID messageId;

    @OneToOne
    @Column(name = "Author")
    private UserEntity userEntity;

    @Basic
    @Column(name = "SentTime")
    private java.sql.Time sentTime;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "MessageType")
    private MessageType messageType;

    //TODO FK to messageId / Null value
//    @Column(name = "ResponseTo")
//    private UUID responseTo;

    @Column(name = "MessageBody",length = 1024)
    private String messageBody;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.REFRESH)
    @JoinColumn(name="UserId")
    protected UserEntity userIdFK;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.REFRESH)
    @JoinColumn(name="ChatId")
    protected UserEntity chatIdFK;
}
