package trabalhoEngSoftware;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import trabalhoEngSoftware.controller.request.CreateTaskRequest;
import trabalhoEngSoftware.controller.response.IdResponse;
import trabalhoEngSoftware.domain.Priority;
import trabalhoEngSoftware.domain.Status;
import trabalhoEngSoftware.domain.Task;
import trabalhoEngSoftware.domain.Users;
import trabalhoEngSoftware.mapper.CreateTaskMapper;
import trabalhoEngSoftware.mapper.IdResponseMapper;
import trabalhoEngSoftware.repository.TaskRepository;
import trabalhoEngSoftware.repository.UserRepository;
import trabalhoEngSoftware.service.CreateTaskService;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CreateTaskServiceTest {

    @Mock
    private TaskRepository taskRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private CreateTaskService tested;

    @Captor
    ArgumentCaptor<Task> taskCaptor;

    @Captor
    ArgumentCaptor<Users> userCaptor;

    @Test
    @DisplayName("Deve criar tarefa com sucesso")
    void deveCriarTarefaComSucesso() {
        Long responsibleId = 1L;
        CreateTaskRequest request = new CreateTaskRequest();
        request.setResponsibleId(responsibleId);
        request.setTitle("Título da Tarefa");
        request.setDescription("Descrição da tarefa");
        request.setPriority(Priority.HIGH);
        request.setStatus(Status.TO_DO);

        Users user = new Users();
        user.setId(responsibleId);

        Task task = new Task();

        when(userRepository.getReferenceById(responsibleId)).thenReturn(user);
        when(taskRepository.save(any(Task.class))).thenReturn(task);

        IdResponse response = tested.incluir(request);

        verify(taskRepository).save(taskCaptor.capture());
        verify(userRepository).save(userCaptor.capture());

        Task taskSalva = taskCaptor.getValue();
        Users userAtualizado = userCaptor.getValue();

        assertEquals(Priority.HIGH, taskSalva.getPriority());
        assertEquals(Status.TO_DO, taskSalva.getStatus());
        assertEquals(user, userAtualizado);
        assertTrue(userAtualizado.getTasks().contains(taskSalva));
    }
}