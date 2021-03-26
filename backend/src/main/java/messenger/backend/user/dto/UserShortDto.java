package messenger.backend.user.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import messenger.backend.user.UserEntity;

import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class UserShortDto {
    public static UserShortDto fromEntity(UserEntity userEntity) {
        return UserShortDto.builder()
                .id(userEntity.getId())
                .fullName(userEntity.getFullName())
                .username(userEntity.getUsername())
                .bio(userEntity.getBio())
                .build();
    }

    private UUID id;
    private String fullName;
    private String username;
    private String bio;
}
