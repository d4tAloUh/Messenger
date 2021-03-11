package messenger.backend.user.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import messenger.backend.user.UserEntity;

@Data
@Builder
@AllArgsConstructor
public class UserDto {
    public static UserDto from(UserEntity userEntity) {
        return UserDto.builder()
                .id(userEntity.getId())
                .username(userEntity.getUsername())
                .fullName(userEntity.getUsername())
                .build();
    }

    private String id;
    private String username;
    private String fullName;
}
