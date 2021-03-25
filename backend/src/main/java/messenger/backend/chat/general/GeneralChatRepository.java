package messenger.backend.chat.general;

import messenger.backend.chat.ChatSuperclass;
import messenger.backend.chat.GroupChatEntity;
import messenger.backend.chat.PrivateChatEntity;
import messenger.backend.user.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface GeneralChatRepository extends JpaRepository<ChatSuperclass, UUID> {

    @Query("SELECT uc.chat " +
            "FROM UserChat as uc " +
            "WHERE uc.user.id = :userId")
    List<ChatSuperclass> findAllByMemberId(UUID userId);
}
