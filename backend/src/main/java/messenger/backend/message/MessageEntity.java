package messenger.backend.message;


import lombok.*;
import messenger.backend.chat.ChatSuperclass;
import messenger.backend.seeds.FakerService;
import messenger.backend.user.UserEntity;
import messenger.backend.userChat.UserChat;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Date;
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
    public static MessageEntity generateMessage(UserChat userChat) {

        return MessageEntity.builder()
                .user(userChat.getUser())
                .chat(userChat.getChat())
                .messageBody(FakerService.faker.elderScrolls().quote())
                .build();
    }

    public static MessageEntity generateGroupChatMessage(UserChat userChat) {

        return MessageEntity.builder()
                .user(userChat.getUser())
                .chat(userChat.getChat())
                .messageBody(FakerService.faker.book().title())
                .build();
    }

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    @Column(name = "MessageId")
    private UUID id;

    @Column(name = "SentTime", nullable = false)
    @Builder.Default
    private Date createdAt = new Date();

    @Column(name = "MessageBody",length = 1024, nullable = false)
    private String messageBody;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.REFRESH)
    @JoinColumn(name="User", nullable = false)
    private UserEntity user;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.REFRESH)
    @JoinColumn(name="Chat", nullable = false)
    private ChatSuperclass chat;
}
