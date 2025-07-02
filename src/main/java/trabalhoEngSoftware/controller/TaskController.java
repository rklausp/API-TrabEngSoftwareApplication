package trabalhoEngSoftware.controller;


import org.springframework.format.annotation.DateTimeFormat;
import trabalhoEngSoftware.controller.request.UpdateTaskRequest;
import trabalhoEngSoftware.controller.request.CreateTaskRequest;
import trabalhoEngSoftware.controller.response.IdResponse;
import trabalhoEngSoftware.controller.response.TaskResponse;
import trabalhoEngSoftware.domain.Priority;
import trabalhoEngSoftware.domain.Status;
import trabalhoEngSoftware.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/tasks")
public class TaskController {

    @Autowired
    CreateTaskService createTaskService;

    @Autowired
    UpdateTaskService updateTaskService;

    @Autowired
    DeleteTaskService deleteTaskService;

    @Autowired
    GetTaskService getTaskService;

    @Autowired
    GetTaskByUserService getTaskByUserService;

    @Autowired
    GetTaskByParametersService getTaskByParametersService;


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public IdResponse create(@Valid @RequestBody CreateTaskRequest request){
        return createTaskService.incluir(request);
    }

    @PutMapping("/{id}")
    public TaskResponse update(@Valid @RequestBody UpdateTaskRequest request, @PathVariable Long id){
        return updateTaskService.update(id, request);
    }

    @PutMapping("{taskId}/add-responsible/{userId}")
    public TaskResponse addResponsible(@PathVariable Long userId, @PathVariable Long taskId){
        return updateTaskService.addResponsible(userId, taskId);
    }

    @PutMapping("{taskId}/remove-responsible/{userId}")
    public TaskResponse removeResponsible(@PathVariable Long userId, @PathVariable Long taskId){
        return updateTaskService.removeResponsible(userId, taskId);
    }

    @DeleteMapping("/{id}")
    public void remover(@PathVariable Long id) {
        deleteTaskService.remove(id);
    }

    @GetMapping("/{id}")
    public TaskResponse search(@PathVariable Long id){
        return getTaskService.search(id);
    }

    @GetMapping("/assigned-to/{userId}")
    public List<TaskResponse> get(@PathVariable Long userId){
        return getTaskByUserService.getTasksAssignedTo(userId);
    }

    @GetMapping("{id}/status/{status}/priority/{priority}/dueBefore/{dueBefore}")
    public List<TaskResponse> getTasksByPath(
            @PathVariable Long id,
            @PathVariable Status status,
            @PathVariable Priority priority,
            @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dueBefore
    ) {
        return getTaskByParametersService.findTasks(id, status, priority, dueBefore);
    }

}
