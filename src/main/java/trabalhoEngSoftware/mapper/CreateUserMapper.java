package trabalhoEngSoftware.mapper;

import trabalhoEngSoftware.controller.request.CreateUserRequest;
import trabalhoEngSoftware.domain.Users;

public class CreateUserMapper {

    public static Users toEntity(CreateUserRequest request){
        return Users
                .builder()
                .name(request.getName())
                .username(request.getUsername())
                .password(request.getPassword())
                .isDeleted(false)
                .build();

    }
}
