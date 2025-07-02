package trabalhoEngSoftware;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;
import trabalhoEngSoftware.controller.request.UserUpdateRequest;
import trabalhoEngSoftware.controller.response.UserResponse;
import trabalhoEngSoftware.domain.Users;
import trabalhoEngSoftware.mapper.UserMapper;
import trabalhoEngSoftware.repository.UserRepository;
import trabalhoEngSoftware.service.UpdateUserService;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UpdateUserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UpdateUserService tested;

    @Captor
    ArgumentCaptor<Users> userCaptor;

    @Test
    void deveAtualizarUsuarioComSucesso() {
        Long userId = 1L;
        UserUpdateRequest request = new UserUpdateRequest();
        request.setName("Novo Nome");
        request.setUsername("novoUsername");
        request.setPassword("novaSenha");

        Users existingUser = new Users();
        existingUser.setId(userId);
        existingUser.setName("Antigo Nome");
        existingUser.setUsername("antigoUsername");
        existingUser.setPassword("antigaSenha");

        when(userRepository.findById(userId)).thenReturn(java.util.Optional.of(existingUser));
        when(userRepository.save(any(Users.class))).thenAnswer(invocation -> invocation.getArgument(0));

        UserResponse response = tested.update(userId, request);

        verify(userRepository).findById(userId);
        verify(userRepository).save(userCaptor.capture());

        Users savedUser = userCaptor.getValue();

        assertEquals("Novo Nome", savedUser.getName());
        assertEquals("novoUsername", savedUser.getUsername());
        assertEquals("novaSenha", savedUser.getPassword());

        assertEquals(savedUser.getId(), response.getId());
        assertEquals(savedUser.getName(), response.getName());
        assertEquals(savedUser.getUsername(), response.getUsername());
    }

    @Test
    void deveLancarExcecaoQuandoUsuarioNaoExistir() {
        Long userId = 999L;
        UserUpdateRequest request = new UserUpdateRequest();

        when(userRepository.findById(userId)).thenReturn(java.util.Optional.empty());

        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> {
            tested.update(userId, request);
        });

        assertEquals("400 BAD_REQUEST \"Task nao encontrada\"", exception.getMessage());
        verify(userRepository, never()).save(any());
    }
}
