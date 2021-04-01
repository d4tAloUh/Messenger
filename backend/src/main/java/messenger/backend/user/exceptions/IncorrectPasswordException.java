package messenger.backend.user.exceptions;

import messenger.backend.utils.exceptions.WebException;
import org.springframework.http.HttpStatus;

public class IncorrectPasswordException extends WebException {

    public IncorrectPasswordException() {
        super("Incorrect password", HttpStatus.CONFLICT);
    }

}
