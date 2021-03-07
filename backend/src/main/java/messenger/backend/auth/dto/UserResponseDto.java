package messenger.backend.auth.dto;

import lombok.Data;
import messenger.backend.user.UserEntity;

@Data

public class UserResponseDto {
    public static UserResponseDto from(UserEntity userEntity) {
        UserResponseDto responseDto = new UserResponseDto();
        responseDto.setId(userEntity.getId());
        responseDto.setUsername(userEntity.getUsername());
        responseDto.setFullName(userEntity.getFullName());
        return responseDto;
    }

    private String id;
    private String username;
    private String fullName;
}
