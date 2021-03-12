package messenger.backend.auth.exceptions;

import messenger.backend.utils.exceptions.WebException;
import org.springframework.http.HttpStatus;


public class JwtAuthException extends WebException {

    public JwtAuthException(String message) {
        super(message, HttpStatus.UNAUTHORIZED);
    }

}
