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
@Table(name = "PrivateChat")
public class PrivateChatEntity extends ChatSuperclass {
    @Lob
    @Column(name = "chatPicture")
    private Byte[] chatPicture;
}
