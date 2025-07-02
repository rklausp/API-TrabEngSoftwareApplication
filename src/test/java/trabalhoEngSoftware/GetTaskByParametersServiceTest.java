package trabalhoEngSoftware;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;
import trabalhoEngSoftware.controller.response.TaskResponse;
import trabalhoEngSoftware.domain.Priority;
import trabalhoEngSoftware.domain.Status;
import trabalhoEngSoftware.domain.Task;
import trabalhoEngSoftware.domain.Users;
import trabalhoEngSoftware.repository.TaskRepository;
import trabalhoEngSoftware.repository.UserRepository;
import trabalhoEngSoftware.service.GetTaskByParametersService;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GetTaskByParametersServiceTest {

    @Mock
    private TaskRepository taskRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private GetTaskByParametersService tested;

    @Test
    void deveRetornarListaDeTarefasFiltradasComSucesso() {
        Long userId = 1L;
        Status status = Status.IN_PROGRESS;
        Priority priority = Priority.HIGH;
        LocalDate dueBefore = LocalDate.of(2025, 7, 10);

        Users user = new Users();
        user.setId(userId);
        user.setName("João");

        Task task1 = new Task();
        task1.setId(10L);
        task1.setTitle("Tarefa 1");
        task1.setStatus(status);
        task1.setPriority(priority);
        task1.setDueDate(LocalDate.of(2025, 7, 5));
        task1.setResponsible(List.of(user));

        Task task2 = new Task();
        task2.setId(11L);
        task2.setTitle("Tarefa 2");
        task2.setStatus(status);
        task2.setPriority(priority);
        task2.setDueDate(LocalDate.of(2025, 7, 9));
        task2.setResponsible(List.of(user));

        List<Task> tarefas = List.of(task1, task2);

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(taskRepository.findByResponsibleAndStatusAndPriorityAndDueDateBefore(user, status, priority, dueBefore))
                .thenReturn(tarefas);

        List<TaskResponse> responses = tested.findTasks(userId, status, priority, dueBefore);

        assertNotNull(responses);
        assertEquals(2, responses.size());

        assertEquals("Tarefa 1", responses.get(0).getTitle());
        assertEquals("Tarefa 2", responses.get(1).getTitle());
        assertEquals(status, responses.get(1).getStatus());
        assertEquals(priority, responses.get(1).getPriority());
    }

    @Test
    void deveLancarExcecaoQuandoUsuarioNaoForEncontrado() {
        Long userId = 99L;
        Status status = Status.TO_DO;
        Priority priority = Priority.MEDIUM;
        LocalDate dueBefore = LocalDate.of(2025, 7, 15);

        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        ResponseStatusException exception = assertThrows(ResponseStatusException.class,
                () -> tested.findTasks(userId, status, priority, dueBefore));

        assertEquals("422 UNPROCESSABLE_ENTITY \"Users nao encontrado\"", exception.getMessage());
        verify(taskRepository, never()).findByResponsibleAndStatusAndPriorityAndDueDateBefore(any(), any(), any(), any());
    }
}
