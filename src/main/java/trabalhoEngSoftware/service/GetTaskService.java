package trabalhoEngSoftware.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import trabalhoEngSoftware.controller.response.TaskResponse;
import trabalhoEngSoftware.domain.Task;
import trabalhoEngSoftware.mapper.TaskMapper;
import trabalhoEngSoftware.repository.TaskRepository;

@Service
public class GetTaskService {

    @Autowired
    private TaskRepository taskRepository;

    public TaskResponse search(Long id) {

        Task task = taskRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, "Task nao encontrado"));
        return TaskMapper.toResponse(task);
    }
}
