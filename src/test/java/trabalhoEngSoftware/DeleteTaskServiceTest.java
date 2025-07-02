package trabalhoEngSoftware;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;
import trabalhoEngSoftware.domain.Task;
import trabalhoEngSoftware.repository.TaskRepository;
import trabalhoEngSoftware.service.CommentService;
import trabalhoEngSoftware.service.DeleteTaskService;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class DeleteTaskServiceTest {

    @InjectMocks
    private DeleteTaskService tested;

    @Mock
    private TaskRepository taskRepository;

    @Captor
    private ArgumentCaptor<Task> taskCaptor;

    @Test
    void deveMarcarTarefaComoDeletada() {
        Long taskId = 123L;
        Task task = new Task();
        task.setId(taskId);
        task.setDeleted(false);

        when(taskRepository.findById(taskId)).thenReturn(Optional.of(task));

        tested.remove(taskId);

        verify(taskRepository).findById(taskId);
        verify(taskRepository).save(taskCaptor.capture());

        Task taskSalva = taskCaptor.getValue();
        assertTrue(taskSalva.isDeleted());
    }

    @Test
    public void deveLancarExcecao_QuandoTarefaNaoExiste() {
        // Arrange
        Long taskId = 999L;
        when(taskRepository.findById(taskId)).thenReturn(Optional.empty());

        // Act + Assert
        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> {
            tested.remove(taskId);
        });

        assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, exception.getStatus());
        assertEquals("Task nao encontrado", exception.getReason());
        verify(taskRepository, never()).save(any());
    }
}
