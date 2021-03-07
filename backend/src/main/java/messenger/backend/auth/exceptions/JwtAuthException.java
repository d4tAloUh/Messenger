package messenger.backend.auth.exceptions;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;


@Getter
public class JwtAuthException extends AuthenticationException {

    private HttpStatus httpStatus;

    public JwtAuthException(String explanation) {
        super(explanation);
    }

    public JwtAuthException(String explanation, HttpStatus httpStatus) {
        super(explanation);
        this.httpStatus = httpStatus;
    }

}
