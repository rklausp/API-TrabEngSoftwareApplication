package trabalhoEngSoftware.mapper;

import trabalhoEngSoftware.controller.response.UsuarioTarefasResponse;
import trabalhoEngSoftware.domain.Users;

public class UsuarioTarefasMapper {

    public static UsuarioTarefasResponse toResponse(Users entity){
        return UsuarioTarefasResponse.builder()
                .nome(entity.getName())
                .tasks(entity.getTasks())
                .build();
    }

}
