package trabalhoEngSoftware.controller.request;

import lombok.Getter;
import lombok.Setter;
import trabalhoEngSoftware.domain.Priority;
import trabalhoEngSoftware.domain.Status;

import java.time.LocalDate;

@Getter @Setter
public class UpdateTaskRequest {

    private String title;
    private String description;
    private Priority priority;
    private Status status;
    private Long responsibleId;
    private LocalDate dueDate;
}
