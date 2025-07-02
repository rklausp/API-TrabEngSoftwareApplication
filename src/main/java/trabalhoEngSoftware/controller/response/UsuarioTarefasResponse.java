package trabalhoEngSoftware.controller.response;

import trabalhoEngSoftware.domain.Task;
import lombok.*;

import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UsuarioTarefasResponse {

    private String nome;

    private List<Task> tasks;
}
