package trabalhoEngSoftware.service;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;
import trabalhoEngSoftware.domain.Task;
import trabalhoEngSoftware.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class DeleteTaskService {

    @Autowired
    TaskRepository taskRepository;

    @Transactional
    public void remove(Long id){

        Task task = taskRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, "Task nao encontrado"));
        task.setDeleted(true);
        taskRepository.save(task);
    }
}
