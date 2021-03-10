package messenger.backend.utils.exceptions;

import lombok.Value;

@Value
public class ValidationException extends RuntimeException {
    String message;
}
