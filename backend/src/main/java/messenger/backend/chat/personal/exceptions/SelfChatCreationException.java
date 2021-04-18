package messenger.backend.chat.personal.exceptions;

import messenger.backend.utils.exceptions.WebException;
import org.springframework.http.HttpStatus;

public class SelfChatCreationException extends WebException {
    public SelfChatCreationException() {
        super("You can't create chat with yourself", HttpStatus.CONFLICT);
    }
}
