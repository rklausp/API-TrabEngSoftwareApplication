package trabalhoEngSoftware.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import trabalhoEngSoftware.controller.request.CreateCommentRequest;
import trabalhoEngSoftware.controller.response.CommentResponse;
import trabalhoEngSoftware.domain.Comment;
import trabalhoEngSoftware.domain.Task;
import trabalhoEngSoftware.domain.Users;
import trabalhoEngSoftware.mapper.CommentMapper;
import trabalhoEngSoftware.repository.CommentRepository;
import trabalhoEngSoftware.repository.TaskRepository;
import trabalhoEngSoftware.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentService {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private UserRepository userRepository;


    public CommentResponse addComment(Long taskId, CreateCommentRequest request) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new RuntimeException("Task not found"));

        Users users = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new RuntimeException("Users not found"));

        Comment comment = CommentMapper.toEntity(request.getContent(), LocalDateTime.now(), users, task);

        commentRepository.save(comment);

        return CommentMapper.toResponse(comment);
    }

    public List<CommentResponse> getCommentsByTaskId(Long taskId) {
        List<Comment> comments = commentRepository.findByTaskIdAndIsDeleted(taskId,false);
        return comments.stream().map(CommentMapper::toResponse).collect(Collectors.toList());
    }

    public void deleteComment(Long commentId) {

        Comment comment = commentRepository.findById(commentId).orElseThrow(() -> new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, "Comment nao encontrado"));
        comment.setDeleted(true);
        commentRepository.save(comment);
    }
}
