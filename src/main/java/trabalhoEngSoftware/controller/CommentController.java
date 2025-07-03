package trabalhoEngSoftware.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import trabalhoEngSoftware.controller.request.CreateCommentRequest;
import trabalhoEngSoftware.controller.response.CommentResponse;
import trabalhoEngSoftware.service.CommentService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/tasks/{taskId}/comments")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @PostMapping
    public CommentResponse createComment(@PathVariable Long taskId, @Valid @RequestBody CreateCommentRequest request) {
        return commentService.addComment(taskId, request);
    }

    @GetMapping
    public List<CommentResponse> getComments(@PathVariable Long taskId) {
        return commentService.getCommentsByTaskId(taskId);
    }

    @DeleteMapping("/{commentId}")
    public void deleteComment(@PathVariable Long commentId) {
        commentService.deleteComment(commentId);
    }
}
