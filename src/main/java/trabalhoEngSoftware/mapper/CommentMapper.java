package trabalhoEngSoftware.mapper;

import trabalhoEngSoftware.controller.response.CommentResponse;
import trabalhoEngSoftware.domain.Comment;
import trabalhoEngSoftware.domain.Task;
import trabalhoEngSoftware.domain.Users;

import java.time.LocalDateTime;

public class CommentMapper {

    public static Comment toEntity(String content, LocalDateTime createdAt, Users users, Task task){
        return Comment
                .builder()
                .users(users)
                .content(content)
                .createdAt(createdAt)
                .task(task)
                .isDeleted(false)
                .build();

    }

    public static CommentResponse toResponse(Comment entity){
        return CommentResponse
                .builder()
                .id(entity.getId())
                .content(entity.getContent())
                .createdAt(entity.getCreatedAt())
                .createdBy(entity.getUsers().getName())
                .task(entity.getTask().getTitle())
                .build();
    }
}
