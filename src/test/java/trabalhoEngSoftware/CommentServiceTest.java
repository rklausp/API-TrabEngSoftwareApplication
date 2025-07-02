package trabalhoEngSoftware;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import trabalhoEngSoftware.controller.request.CreateCommentRequest;
import trabalhoEngSoftware.controller.response.CommentResponse;
import trabalhoEngSoftware.domain.Comment;
import trabalhoEngSoftware.domain.Task;
import trabalhoEngSoftware.domain.Users;
import trabalhoEngSoftware.repository.CommentRepository;
import trabalhoEngSoftware.repository.TaskRepository;
import trabalhoEngSoftware.repository.UserRepository;
import trabalhoEngSoftware.service.CommentService;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CommentServiceTest {

    @Mock
    private CommentRepository commentRepository;

    @Mock
    private TaskRepository taskRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private CommentService tested;

    @Captor
    ArgumentCaptor<Comment> commentCaptor;

    @Test
    void deveAdicionarComentarioComSucesso() {
        Long taskId = 1L;
        CreateCommentRequest request = new CreateCommentRequest();
        request.setUserId(10L);
        request.setContent("Comentário legal");

        Task task = new Task();
        task.setId(taskId);

        Users user = new Users();
        user.setId(request.getUserId());
        user.setName("Maria");

        when(taskRepository.findById(taskId)).thenReturn(Optional.of(task));
        when(userRepository.findById(request.getUserId())).thenReturn(Optional.of(user));
        when(commentRepository.save(any(Comment.class))).thenAnswer(invocation -> invocation.getArgument(0));

        CommentResponse response = tested.addComment(taskId, request);

        verify(commentRepository).save(commentCaptor.capture());
        Comment salvo = commentCaptor.getValue();

        assertEquals(request.getContent(), salvo.getContent());
        assertEquals(user, salvo.getUsers());
        assertEquals(task, salvo.getTask());
        assertNotNull(salvo.getCreatedAt());

        assertEquals(salvo.getContent(), response.getContent());
        assertEquals(user.getName(), response.getCreatedBy());
    }

    @Test
    void deveRetornarListaDeCommentsQuandoExistirem() {
        Long taskId = 1L;

        Users user = new Users();
        user.setId(10L);
        user.setName("Maria");

        Task task = new Task();
        task.setId(taskId);
        task.setTitle("Titulo teste");

        Comment comment1 = new Comment();
        comment1.setId(100L);
        comment1.setContent("Comentário 1");
        comment1.setUsers(user);
        comment1.setTask(task);

        Comment comment2 = new Comment();
        comment2.setId(101L);
        comment2.setContent("Comentário 2");
        comment2.setUsers(user);
        comment2.setTask(task);

        List<Comment> listaComments = List.of(comment1, comment2);

        when(commentRepository.findByTaskId(taskId)).thenReturn(listaComments);

        List<CommentResponse> responses = tested.getCommentsByTaskId(taskId);

        assertNotNull(responses);
        assertEquals(2, responses.size());

        assertEquals(comment1.getContent(), responses.get(0).getContent());
        assertEquals(comment2.getContent(), responses.get(1).getContent());

        assertEquals(user.getName(), responses.get(0).getCreatedBy());
        assertEquals(task.getTitle(), responses.get(0).getTask());
    }

    @Test
    void deveMarcarComentarioComoDeletado() {
        Long commentId = 42L;

        Comment comment = new Comment();
        comment.setId(commentId);
        comment.setDeleted(false);

        when(commentRepository.findById(commentId)).thenReturn(Optional.of(comment));

        tested.deleteComment(commentId);

        verify(commentRepository).findById(commentId);
        verify(commentRepository).save(commentCaptor.capture());

        Comment comentarioSalvo = commentCaptor.getValue();
        assertTrue(comentarioSalvo.isDeleted());
        assertEquals(commentId, comentarioSalvo.getId());
    }
}