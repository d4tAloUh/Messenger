package messenger.backend.chat;

import lombok.*;
import messenger.backend.seeds.FakerService;

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

    public static GroupChatEntity generateGroupChat() {

        return GroupChatEntity.builder()
                .groupName(FakerService.faker.funnyName().name())
                .build();
    }

    @Column(name = "GroupName", length = 64)
    private String groupName;

    @Lob
    @Column(name = "GroupPicture")
    private Byte[] groupPicture;

}
