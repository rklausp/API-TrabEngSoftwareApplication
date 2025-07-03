package trabalhoEngSoftware;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;
import trabalhoEngSoftware.controller.response.TaskResponse;
import trabalhoEngSoftware.domain.Task;
import trabalhoEngSoftware.mapper.TaskMapper;
import trabalhoEngSoftware.repository.TaskRepository;
import trabalhoEngSoftware.service.GetTaskService;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GetTaskServiceTest {

    @Mock
    private TaskRepository taskRepository;

    @InjectMocks
    private GetTaskService tested;

    @Test
    void shouldReturnTaskWhenFound() {
        Long taskId = 1L;
        Task task = new Task();
        task.setId(taskId);
        task.setTitle("Tarefa teste");
        task.setDeleted(false);

        when(taskRepository.findById(taskId)).thenReturn(Optional.of(task));

        TaskResponse response = tested.search(taskId);

        verify(taskRepository).findById(taskId);
        assertNotNull(response);
        assertEquals(task.getId(), response.getId());
        assertEquals(task.getTitle(), response.getTitle());
    }

    @Test
    void shouldRaiseExceptionForTaskNotFound() {
        Long taskId = 999L;

        when(taskRepository.findById(taskId)).thenReturn(Optional.empty());

        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> tested.search(taskId));

        assertEquals("422 UNPROCESSABLE_ENTITY \"Task nao encontrado\"", exception.getMessage());
    }
}
