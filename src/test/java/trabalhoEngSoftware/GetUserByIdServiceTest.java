package trabalhoEngSoftware;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;
import trabalhoEngSoftware.controller.response.UserResponse;
import trabalhoEngSoftware.domain.Users;
import trabalhoEngSoftware.repository.UserRepository;
import trabalhoEngSoftware.service.GetUserByIdService;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GetUserByIdServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private GetUserByIdService tested;

    @Test
    void deveRetornarUserQuandoEncontrar() {
        Long userId = 1L;
        Users user = new Users();
        user.setId(userId);
        user.setName("João");

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        UserResponse response = tested.get(userId);

        verify(userRepository).findById(userId);
        assertNotNull(response);
        assertEquals(user.getId(), response.getId());
        assertEquals(user.getName(), response.getName());
    }

    @Test
    void deveLancarExcecaoQuandoUserNaoEncontrado() {
        Long userId = 999L;

        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> tested.get(userId));

        assertEquals("422 UNPROCESSABLE_ENTITY \"Users not found\"", exception.getMessage());
        verify(userRepository).findById(userId);
    }
}