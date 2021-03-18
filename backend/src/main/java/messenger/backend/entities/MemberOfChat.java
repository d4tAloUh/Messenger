package messenger.backend.entities;


import javax.persistence.*;

import lombok.*;

import java.io.Serializable;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString

@Entity
public class MemberOfChat {
    public enum PermissionLevel {
        OWNER,
        ADMIN,
        MEMBER
    }

    @EmbeddedId
    private MemberOfChatPK memberOfChatPK;

    @Enumerated
    @Column(name = "PermissionLevel")
    private PermissionLevel permissionLevel;

    @Embeddable
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    @EqualsAndHashCode
    public static class MemberOfChatPK implements Serializable {
        @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.REFRESH)
        @JoinColumn(name="UserId")
        protected UserEntity userIdFK;

        @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.REFRESH)
        @JoinColumn(name="ChatId")
        protected ChatSuperclass chatIdFK;
    }
}
