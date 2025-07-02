package trabalhoEngSoftware.service;

import trabalhoEngSoftware.controller.request.UpdateTaskRequest;
import trabalhoEngSoftware.controller.response.TaskResponse;
import trabalhoEngSoftware.domain.Task;
import trabalhoEngSoftware.domain.Users;
import trabalhoEngSoftware.mapper.TaskMapper;
import trabalhoEngSoftware.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import trabalhoEngSoftware.repository.UserRepository;

@Service
public class UpdateTaskService {

    @Autowired
    TaskRepository taskRepository;

    @Autowired
    UserRepository userRepository;

    public TaskResponse update(Long id, UpdateTaskRequest request) {

        Task task = taskRepository.findById(id)
                                        .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Task nao encontrada"));

        if (request.getTitle() != null) {
            task.setTitle(request.getTitle());
        }

        if (request.getDescription() != null) {
            task.setDescription(request.getDescription());
        }

        if (request.getPriority() != null) {
            task.setPriority(request.getPriority());
        }

        if (request.getStatus() != null) {
            task.setStatus(request.getStatus());
        }

        if (request.getDueDate() != null) {
            task.setDueDate(request.getDueDate());
        }

        if (request.getResponsibleId() != null) {
            Users user = userRepository.findById(request.getResponsibleId())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Usuário responsável não encontrado"));
            task.getResponsible().add(user);
        }

        taskRepository.save(task);

        return TaskMapper.toResponse(task);
    }

    public TaskResponse addResponsible(Long taskId, Long userId){
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Task nao encontrada"));

        Users user = userRepository.findById(userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Usuário responsável não encontrado"));

        task.getResponsible().add(user);

        taskRepository.save(task);

        return TaskMapper.toResponse(task);
    }

    public TaskResponse removeResponsible(Long taskId, Long userId){
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Task nao encontrada"));

        Users user = userRepository.findById(userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Usuário responsável não encontrado"));

        if (!task.getResponsible().contains(user)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Usuário não está atribuído a esta tarefa");
        }

        task.getResponsible().remove(user);

        taskRepository.save(task);

        return TaskMapper.toResponse(task);
    }
}
