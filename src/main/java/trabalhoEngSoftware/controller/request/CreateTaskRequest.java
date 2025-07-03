package trabalhoEngSoftware.controller.request;

import lombok.Getter;
import lombok.Setter;
import trabalhoEngSoftware.domain.Priority;
import trabalhoEngSoftware.domain.Status;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Getter @Setter
public class CreateTaskRequest {

    @NotBlank
    private String title;
    @NotBlank
    private String description;
    @NotNull
    private Priority priority;
    @NotNull
    private Status status;
    @NotNull
    private Long responsibleId;
    @NotNull
    private LocalDate dueDate;
}
