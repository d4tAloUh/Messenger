package messenger.backend.chat;


import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import messenger.backend.userChat.UserChat;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Inheritance(strategy=InheritanceType.TABLE_PER_CLASS)
public class ChatSuperclass {

    @Id
    @Column(name = "ChatId")
    private UUID id;

    @OneToMany(mappedBy = "chat")
    private List<UserChat> userChats = new ArrayList<>();

}
