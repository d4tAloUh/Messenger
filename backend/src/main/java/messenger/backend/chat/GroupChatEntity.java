package messenger.backend.chat;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.Table;


@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString


@Entity
@Table(name = "GroupChat")

public class GroupChatEntity extends ChatSuperclass {

    @Column(name = "GroupName", length = 64)
    private String groupName;

    @Lob
    @Column(name = "GroupPicture")
    private Byte[] groupPicture;

}
