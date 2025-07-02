package trabalhoEngSoftware;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;
import trabalhoEngSoftware.controller.request.UpdateTaskRequest;
import trabalhoEngSoftware.controller.response.TaskResponse;
import trabalhoEngSoftware.domain.Priority;
import trabalhoEngSoftware.domain.Status;
import trabalhoEngSoftware.domain.Task;
import trabalhoEngSoftware.domain.Users;
import trabalhoEngSoftware.repository.TaskRepository;
import trabalhoEngSoftware.repository.UserRepository;
import trabalhoEngSoftware.service.UpdateTaskService;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UpdateTaskServiceTest {

    @Mock
    private TaskRepository taskRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UpdateTaskService tested;

    @Captor
    ArgumentCaptor<Task> taskCaptor;

    @Test
    void deveAtualizarTarefaComSucesso() {
        Long taskId = 1L;
        UpdateTaskRequest request = new UpdateTaskRequest();
        request.setTitle("Novo título");
        request.setDescription("Nova descrição");
        request.setPriority(Priority.MEDIUM);
        request.setStatus(Status.IN_PROGRESS);
        request.setDueDate(LocalDate.of(2025, 7, 31));
        request.setResponsibleId(2L);

        Task task = new Task();
        task.setId(taskId);
        task.setResponsible(new ArrayList<>());

        Users user = new Users();
        user.setId(2L);

        when(taskRepository.findById(taskId)).thenReturn(Optional.of(task));
        when(userRepository.findById(2L)).thenReturn(Optional.of(user));
        when(taskRepository.save(any(Task.class))).thenAnswer(invocation -> invocation.getArgument(0));

        TaskResponse response = tested.update(taskId, request);

        verify(taskRepository).save(taskCaptor.capture());
        Task savedTask = taskCaptor.getValue();

        assertEquals("Novo título", savedTask.getTitle());
        assertEquals("Nova descrição", savedTask.getDescription());
        assertEquals(Priority.MEDIUM, savedTask.getPriority());
        assertEquals(Status.IN_PROGRESS, savedTask.getStatus());
        assertEquals(LocalDate.of(2025, 7, 31), savedTask.getDueDate());
        assertTrue(savedTask.getResponsible().contains(user));

        assertEquals(savedTask.getId(), response.getId());
    }

    @Test
    void deveLancarExcecaoQuandoTarefaNaoEncontradaNaAtualizacao() {
        when(taskRepository.findById(1L)).thenReturn(Optional.empty());

        UpdateTaskRequest request = new UpdateTaskRequest();

        ResponseStatusException exception = assertThrows(ResponseStatusException.class,
                () -> tested.update(1L, request));

        assertEquals("400 BAD_REQUEST \"Task nao encontrada\"", exception.getMessage());
    }

    @Test
    void deveLancarExcecaoQuandoUserNaoEncontradoNaAtualizacao() {
        Long taskId = 1L;
        Long responsibleId = 2L;

        Task task = new Task();
        task.setId(taskId);
        task.setResponsible(new ArrayList<>());

        UpdateTaskRequest request = new UpdateTaskRequest();
        request.setResponsibleId(responsibleId);

        when(taskRepository.findById(taskId)).thenReturn(Optional.of(task));
        when(userRepository.findById(responsibleId)).thenReturn(Optional.empty());

        ResponseStatusException exception = assertThrows(ResponseStatusException.class,
                () -> tested.update(taskId, request));

        assertEquals("400 BAD_REQUEST \"Usuário responsável não encontrado\"", exception.getMessage());
    }

    @Test
    void deveAdicionarResponsavelComSucesso() {
        Long taskId = 1L;
        Long userId = 2L;

        Task task = new Task();
        task.setId(taskId);
        task.setResponsible(new ArrayList<>());

        Users user = new Users();
        user.setId(userId);

        when(taskRepository.findById(taskId)).thenReturn(Optional.of(task));
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(taskRepository.save(any(Task.class))).thenAnswer(invocation -> invocation.getArgument(0));

        TaskResponse response = tested.addResponsible(taskId, userId);

        verify(taskRepository).save(taskCaptor.capture());
        Task savedTask = taskCaptor.getValue();

        assertTrue(savedTask.getResponsible().contains(user));
        assertEquals(savedTask.getId(), response.getId());
    }

    @Test
    void deveRemoverResponsavelComSucesso() {
        Long taskId = 1L;
        Long userId = 2L;

        Users user = new Users();
        user.setId(userId);

        Task task = new Task();
        task.setId(taskId);
        task.setResponsible(new ArrayList<>());
        task.getResponsible().add(user);

        when(taskRepository.findById(taskId)).thenReturn(Optional.of(task));
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(taskRepository.save(any(Task.class))).thenAnswer(invocation -> invocation.getArgument(0));

        TaskResponse response = tested.removeResponsible(taskId, userId);

        verify(taskRepository).save(taskCaptor.capture());
        Task savedTask = taskCaptor.getValue();

        assertFalse(savedTask.getResponsible().contains(user));
        assertEquals(savedTask.getId(), response.getId());
    }

    @Test
    void deveLancarExcecaoAoRemoverResponsavelNaoExistente() {
        Long taskId = 1L;
        Long userId = 2L;

        Users user = new Users();
        user.setId(userId);

        Task task = new Task();
        task.setId(taskId);
        task.setResponsible(new ArrayList<>());

        when(taskRepository.findById(taskId)).thenReturn(Optional.of(task));
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        ResponseStatusException exception = assertThrows(ResponseStatusException.class,
                () -> tested.removeResponsible(taskId, userId));

        assertEquals("400 BAD_REQUEST \"Usuário não está atribuído a esta tarefa\"", exception.getMessage());
    }

    @Test
    void deveLancarExcecaoQuandoTarefaNaoEncontradaNaAdicao() {
        when(taskRepository.findById(1L)).thenReturn(Optional.empty());

        ResponseStatusException exception = assertThrows(ResponseStatusException.class,
                () -> tested.addResponsible(1L, 2L));

        assertEquals("400 BAD_REQUEST \"Task nao encontrada\"", exception.getMessage());
    }

    @Test
    void deveLancarExcecaoQuandoUserNaoEncontradoNaAdicao() {
        Long taskId = 1L;

        Task task = new Task();
        task.setId(taskId);
        task.setResponsible(new ArrayList<>());

        when(taskRepository.findById(taskId)).thenReturn(Optional.of(task));
        when(userRepository.findById(2L)).thenReturn(Optional.empty());

        ResponseStatusException exception = assertThrows(ResponseStatusException.class,
                () -> tested.addResponsible(taskId, 2L));

        assertEquals("400 BAD_REQUEST \"Usuário responsável não encontrado\"", exception.getMessage());
    }

    @Test
    void deveLancarExcecaoQuandoTarefaNaoEncontradaNaRemocao() {
        when(taskRepository.findById(1L)).thenReturn(Optional.empty());

        ResponseStatusException exception = assertThrows(ResponseStatusException.class,
                () -> tested.removeResponsible(1L, 2L));

        assertEquals("400 BAD_REQUEST \"Task nao encontrada\"", exception.getMessage());
    }

    @Test
    void deveLancarExcecaoQuandoUserNaoEncontradoNaRemocao() {
        Long taskId = 1L;

        Task task = new Task();
        task.setId(taskId);
        task.setResponsible(new ArrayList<>());

        when(taskRepository.findById(taskId)).thenReturn(Optional.of(task));
        when(userRepository.findById(2L)).thenReturn(Optional.empty());

        ResponseStatusException exception = assertThrows(ResponseStatusException.class,
                () -> tested.removeResponsible(taskId, 2L));

        assertEquals("400 BAD_REQUEST \"Usuário responsável não encontrado\"", exception.getMessage());
    }
}