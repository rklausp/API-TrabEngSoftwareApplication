package trabalhoEngSoftware;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;
import trabalhoEngSoftware.domain.Users;
import trabalhoEngSoftware.repository.TaskRepository;
import trabalhoEngSoftware.repository.UserRepository;
import trabalhoEngSoftware.service.DeleteTaskService;
import trabalhoEngSoftware.service.DeleteUserService;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class DeleteUserServiceTest {

    @InjectMocks
    private DeleteUserService deleteUserService;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private DeleteUserService tested;

    @Test
    public void shouldSetUserAsDeleted() {
        Long userId = 1L;
        Users user = new Users();
        user.setId(userId);
        user.setDeleted(false);

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        tested.delete(userId);

        assertTrue(user.isDeleted());
        verify(userRepository).save(user);
    }

    @Test
    public void shouldRaiseExceptionWhenUserDoesNotExist() {
        // Arrange
        Long userId = 999L;
        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        // Act + Assert
        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> {
            tested.delete(userId);
        });

        assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, exception.getStatus());
        assertEquals("Users not found", exception.getReason());
        verify(userRepository, never()).save(any());
    }
}
