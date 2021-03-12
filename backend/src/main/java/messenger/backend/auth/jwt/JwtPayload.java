package messenger.backend.auth.jwt;

import lombok.Builder;
import lombok.Getter;

import java.util.UUID;

@Builder
@Getter
public class JwtPayload {
    private final UUID id;
}
