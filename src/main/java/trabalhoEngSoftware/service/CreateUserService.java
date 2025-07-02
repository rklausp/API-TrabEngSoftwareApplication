package trabalhoEngSoftware.service;

import trabalhoEngSoftware.controller.request.CreateUserRequest;
import trabalhoEngSoftware.controller.response.IdResponse;
import trabalhoEngSoftware.domain.Users;
import trabalhoEngSoftware.mapper.IdResponseMapper;
import trabalhoEngSoftware.mapper.CreateUserMapper;
import trabalhoEngSoftware.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CreateUserService {

    @Autowired
    private UserRepository userRepository;

    public IdResponse create(CreateUserRequest request){

        Users users = CreateUserMapper.toEntity(request);

        userRepository.save(users);

        return IdResponseMapper.toResponse(users.getId());
    }
}
