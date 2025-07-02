package trabalhoEngSoftware.controller.request;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class CreateCommentRequest {

    @NotNull
    private Long userId;

    @NotBlank
    private String content;
}
