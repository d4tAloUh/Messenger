package messenger.backend.chat;

import lombok.*;

import javax.persistence.*;


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
