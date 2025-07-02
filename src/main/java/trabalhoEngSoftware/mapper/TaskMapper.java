package trabalhoEngSoftware.mapper;

import trabalhoEngSoftware.controller.response.TaskResponse;
import trabalhoEngSoftware.domain.Comment;
import trabalhoEngSoftware.domain.Task;
import trabalhoEngSoftware.domain.Users;

import java.util.stream.Collectors;


public class TaskMapper {

    public static TaskResponse toResponse(Task entity){
        return TaskResponse
                .builder()
                .id(entity.getId())
                .title(entity.getTitle())
                .description(entity.getDescription())
                .priority(entity.getPriority())
                .status(entity.getStatus())
                .dueDate(entity.getDueDate())
                .responsible(entity.getResponsible().stream().map(Users::getName).collect(Collectors.toList()))
                .comment(entity.getComments().stream().map(Comment::getContent).collect(Collectors.toList()))
                .build();
    }
}
