package messenger.backend.entities;


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

    @OneToMany(mappedBy = "chatIdFK")
    private List<MemberOfChat> memberOfChats = new ArrayList<>();

    @OneToMany(mappedBy = "chatIdFK")
    private List<MessageEntity> messageEntities = new ArrayList<>();
}
