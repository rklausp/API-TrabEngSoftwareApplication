package trabalhoEngSoftware;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;
import trabalhoEngSoftware.controller.response.TaskResponse;
import trabalhoEngSoftware.domain.Task;
import trabalhoEngSoftware.domain.Users;
import trabalhoEngSoftware.repository.TaskRepository;
import trabalhoEngSoftware.repository.UserRepository;
import trabalhoEngSoftware.service.GetTaskByUserService;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GetTaskByUserServiceTest {

    @Mock
    private TaskRepository taskRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private GetTaskByUserService tested;

    @Test
    void shouldReturnUserTaskList() {
        Long userId = 1L;

        Users user = new Users();
        user.setId(userId);
        user.setName("Ana");

        Task task1 = new Task();
        task1.setId(101L);
        task1.setTitle("Tarefa A");
        task1.setResponsible(List.of(user));
        task1.setDeleted(false);

        Task task2 = new Task();
        task2.setId(102L);
        task2.setTitle("Tarefa B");
        task2.setResponsible(List.of(user));
        task2.setDeleted(false);

        List<Task> tarefas = List.of(task1, task2);

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(taskRepository.findByResponsibleAndIsDeleted(user, false)).thenReturn(tarefas);

        List<TaskResponse> responses = tested.getTasksAssignedTo(userId);

        assertNotNull(responses);
        assertEquals(2, responses.size());

        assertEquals("Tarefa A", responses.get(0).getTitle());
        assertEquals("Tarefa B", responses.get(1).getTitle());
    }

    @Test
    void shouldRaiseExceptionWhenUserDoesNotExist() {
        Long userId = 999L;

        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        ResponseStatusException exception = assertThrows(ResponseStatusException.class,
                () -> tested.getTasksAssignedTo(userId));

        assertEquals("422 UNPROCESSABLE_ENTITY \"Users nao encontrado\"", exception.getMessage());
    }
}