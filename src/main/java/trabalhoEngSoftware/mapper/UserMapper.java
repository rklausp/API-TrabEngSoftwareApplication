package trabalhoEngSoftware.mapper;

import trabalhoEngSoftware.controller.response.UserResponse;
import trabalhoEngSoftware.domain.Users;

public class UserMapper {

    public static UserResponse toResponse(Users entity){
        return UserResponse
                .builder()
                .id(entity.getId())
                .name(entity.getName())
                .username(entity.getUsername())
                .build();
    }
}
