package trabalhoEngSoftware.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import trabalhoEngSoftware.domain.Users;
import trabalhoEngSoftware.repository.UserRepository;

@Service
public class DeleteUserService {
    @Autowired
    UserRepository userRepository;

    @Transactional
    public void delete(Long id){

        Users users = userRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, "Users not found"));
        users.setDeleted(true);
        userRepository.save(users);
    }
}
