package messenger.backend.chat;



import lombok.*;
import lombok.experimental.SuperBuilder;

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

import static java.util.Objects.isNull;

@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter

@Entity
@Inheritance(strategy=InheritanceType.TABLE_PER_CLASS)
public class ChatSuperclass {
    public void appendUserChat(UserChat userChat){
//        if(isNull(userChats))
//            userChats = new ArrayList<>();
        getUserChats().add(userChat);
    }

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    @Column(name = "ChatId")

    private UUID id;


    @OneToMany(mappedBy = "chat", cascade = {CascadeType.REFRESH, CascadeType.REMOVE})
    private List<UserChat> userChats = new ArrayList<>();

}
