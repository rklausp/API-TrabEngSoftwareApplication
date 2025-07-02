package trabalhoEngSoftware.controller.response;

import lombok.*;

@Builder @AllArgsConstructor @NoArgsConstructor
@Getter @Setter
public class UserResponse {

    private Long id;
    private String name;
    private String username;

}
