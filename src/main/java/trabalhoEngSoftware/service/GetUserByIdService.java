package trabalhoEngSoftware.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import trabalhoEngSoftware.controller.response.UserResponse;
import trabalhoEngSoftware.domain.Users;
import trabalhoEngSoftware.mapper.UserMapper;
import trabalhoEngSoftware.repository.UserRepository;

@Service
public class GetUserByIdService {

    @Autowired
    private UserRepository userRepository;

    public UserResponse get(Long id) {

        Users users = userRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, "Users not found"));
        return UserMapper.toResponse(users);
    }
}
