package messenger.backend.chat;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import messenger.backend.userChat.UserChat;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

@Entity
@Inheritance(strategy=InheritanceType.TABLE_PER_CLASS)
public class ChatSuperclass {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    @Column(name = "ChatId")
    private UUID chatId;

    @OneToMany(mappedBy = "chat")
    private List<UserChat> userChats = new ArrayList<>();

}