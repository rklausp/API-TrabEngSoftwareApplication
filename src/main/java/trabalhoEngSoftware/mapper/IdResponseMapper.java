package trabalhoEngSoftware.mapper;

import trabalhoEngSoftware.controller.response.IdResponse;

public class IdResponseMapper {
    public static IdResponse toResponse(Long id) {
        return IdResponse.builder().id(id).build();
    }
}
