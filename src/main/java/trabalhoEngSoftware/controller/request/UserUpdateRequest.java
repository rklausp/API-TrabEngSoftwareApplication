package trabalhoEngSoftware.controller.request;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter @Setter
public class UserUpdateRequest {

    private String name;

    private String username;

    private String password;
}
