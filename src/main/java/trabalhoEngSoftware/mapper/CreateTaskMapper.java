package trabalhoEngSoftware.mapper;

import trabalhoEngSoftware.controller.request.CreateTaskRequest;
import trabalhoEngSoftware.domain.Task;

import java.util.ArrayList;

public class CreateTaskMapper {

    public static Task toEntity(CreateTaskRequest request){
        return Task
                .builder()
                .title(request.getTitle())
                .description(request.getDescription())
                .status(request.getStatus())
                .priority(request.getPriority())
                .dueDate(request.getDueDate())
                .responsible(new ArrayList<>())
                .comments(new ArrayList<>())
                .isDeleted(false)
                .build();
    }
}
