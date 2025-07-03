package trabalhoEngSoftware.service;

import trabalhoEngSoftware.controller.response.UserResponse;
import trabalhoEngSoftware.domain.Users;
import trabalhoEngSoftware.mapper.UserMapper;
import trabalhoEngSoftware.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GetUserInfoService {

    @Autowired
    private UserRepository userRepository;

    public UserResponse search(String search) {

        Users users = userRepository.findByNameContainingIgnoreCaseAndIsDeleted(search, false);
        if(users == null){
            return null;
        }
        return UserMapper.toResponse(users);
    }
}
