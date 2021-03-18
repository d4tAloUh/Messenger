package messenger.backend.entities;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;


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
