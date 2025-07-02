package trabalhoEngSoftware.service;

import trabalhoEngSoftware.controller.request.CreateTaskRequest;
import trabalhoEngSoftware.controller.response.IdResponse;
import trabalhoEngSoftware.domain.Task;
import trabalhoEngSoftware.domain.Users;
import trabalhoEngSoftware.mapper.IdResponseMapper;
import trabalhoEngSoftware.mapper.CreateTaskMapper;
import trabalhoEngSoftware.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import trabalhoEngSoftware.repository.UserRepository;

@Service
public class CreateTaskService {

    @Autowired
    TaskRepository taskRepository;

    @Autowired
    UserRepository userRepository;

    public IdResponse incluir(CreateTaskRequest request){

        Users users = userRepository.getReferenceById(request.getResponsibleId());
        Task task = CreateTaskMapper.toEntity(request);
        users.addTask(task);
        taskRepository.save(task);
        userRepository.save(users);

        return IdResponseMapper.toResponse(task.getId());
    }
}
