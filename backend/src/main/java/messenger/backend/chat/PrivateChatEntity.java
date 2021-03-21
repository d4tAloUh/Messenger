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
@Table(name = "PrivateChat")
public class PrivateChatEntity extends ChatSuperclass {

    public static PrivateChatEntity generateGroupChat() {

        return PrivateChatEntity.builder()
                .build();
    }

    @Lob
    @Column(name = "chatPicture")
    private Byte[] chatPicture;
}
