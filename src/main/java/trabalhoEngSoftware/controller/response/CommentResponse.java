package trabalhoEngSoftware.controller.response;

import lombok.*;

import java.time.LocalDateTime;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CommentResponse {

    private Long id;

    private String content;

    private LocalDateTime createdAt;

    private String task;

    private String createdBy;
}
