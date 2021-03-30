package messenger.backend.sockets;

import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor

@Component
public class SocketSender {

    private final SimpMessagingTemplate simpMessagingTemplate;

    public void send(Subscribed urlPrefix, List<UUID> uuidList, Object data) {
        uuidList.forEach(uuid -> simpMessagingTemplate.convertAndSend(urlPrefix.toString() + uuid, data));
    }

}
