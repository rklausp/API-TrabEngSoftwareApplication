package trabalhoEngSoftware.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import trabalhoEngSoftware.controller.response.TaskResponse;
import trabalhoEngSoftware.domain.Task;
import trabalhoEngSoftware.domain.Users;
import trabalhoEngSoftware.mapper.TaskMapper;
import trabalhoEngSoftware.repository.TaskRepository;
import trabalhoEngSoftware.repository.UserRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class GetTaskByUserService {

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private UserRepository userRepository;

    public List<TaskResponse> getTasksAssignedTo(Long userId){
        Users users = userRepository.findById(userId).orElseThrow(() -> new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, "Users nao encontrado"));
        List<Task> tasks = taskRepository.findByResponsible(users);
        return tasks.stream().map(TaskMapper::toResponse).collect(Collectors.toList());
    }
}
