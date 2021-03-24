package messenger.backend.chat.group;

import messenger.backend.chat.GroupChatEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.UUID;

public interface GroupChatRepository extends JpaRepository<GroupChatEntity, UUID> {



}
