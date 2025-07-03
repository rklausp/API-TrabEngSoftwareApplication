package trabalhoEngSoftware.controller.response;

import lombok.*;
import trabalhoEngSoftware.domain.Priority;
import trabalhoEngSoftware.domain.Status;

import java.time.LocalDate;
import java.util.List;

@Builder @AllArgsConstructor @NoArgsConstructor
@Getter @Setter
public class TaskResponse {

    private Long id;
    private String title;
    private String description;
    private Priority priority;
    private Status status;
    private LocalDate dueDate;
    private List<String> responsible;
    private List<String> comment;
}
