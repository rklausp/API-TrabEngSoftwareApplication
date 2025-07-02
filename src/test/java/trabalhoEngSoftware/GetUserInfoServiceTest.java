package trabalhoEngSoftware;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import trabalhoEngSoftware.controller.response.UserResponse;
import trabalhoEngSoftware.domain.Users;
import trabalhoEngSoftware.repository.UserRepository;
import trabalhoEngSoftware.service.GetUserInfoService;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GetUserInfoServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private GetUserInfoService tested;

    @Test
    void deveRetornarUserQuandoEncontrarPeloNome() {
        String search = "joao";

        Users user = new Users();
        user.setId(1L);
        user.setName("Joao Silva");

        when(userRepository.findByNameContainingIgnoreCase(search)).thenReturn(user);

        UserResponse response = tested.search(search);

        verify(userRepository).findByNameContainingIgnoreCase(search);
        assertNotNull(response);
        assertEquals(user.getId(), response.getId());
        assertEquals(user.getName(), response.getName());
    }

    @Test
    void deveRetornarNullQuandoNaoEncontrarUser() {
        String search = "nome_inexistente";

        when(userRepository.findByNameContainingIgnoreCase(search)).thenReturn(null);

        UserResponse response = tested.search(search);

        verify(userRepository).findByNameContainingIgnoreCase(search);
        assertNull(response);
    }
}