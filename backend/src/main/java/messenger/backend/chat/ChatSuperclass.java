package messenger.backend.chat;


import messenger.backend.userChat.UserChat;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

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
    private UUID chadId;

    @OneToMany(mappedBy = "chat")
    private List<UserChat> userChats = new ArrayList<>();

}
