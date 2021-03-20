package messenger.backend.userChat;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;


@Repository
public interface UserChatRepository extends JpaRepository<UserChat, UUID> {


}
