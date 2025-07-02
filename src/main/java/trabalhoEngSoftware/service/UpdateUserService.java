package trabalhoEngSoftware.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import trabalhoEngSoftware.controller.request.UserUpdateRequest;
import trabalhoEngSoftware.controller.response.UserResponse;
import trabalhoEngSoftware.domain.Users;
import trabalhoEngSoftware.mapper.UserMapper;
import trabalhoEngSoftware.repository.UserRepository;

@Service
public class UpdateUserService {

    @Autowired
    UserRepository userRepository;

    public UserResponse update(Long id, UserUpdateRequest request) {

        Users users = userRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Task nao encontrada"));

        if (request.getName() != null) {
            users.setName(request.getName());
        }

        if (request.getUsername() != null) {
            users.setUsername(request.getUsername());
        }

        if (request.getPassword() != null) {
            users.setPassword(request.getPassword());
        }

        userRepository.save(users);

        return UserMapper.toResponse(users);
    }
}
