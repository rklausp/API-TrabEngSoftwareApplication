package trabalhoEngSoftware.controller.request;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter @Setter
public class AdicionarTarefaRequest {

    @NotNull
    private Long tarefaId;
}
