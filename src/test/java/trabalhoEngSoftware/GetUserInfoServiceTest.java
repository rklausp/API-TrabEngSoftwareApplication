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
    void shouldReturnUserFoundByName() {
        String search = "joao";

        Users user = new Users();
        user.setId(1L);
        user.setName("Joao Silva");

        when(userRepository.findByNameContainingIgnoreCaseAndIsDeleted(search, false)).thenReturn(user);

        UserResponse response = tested.search(search);

        verify(userRepository).findByNameContainingIgnoreCaseAndIsDeleted(search, false);
        assertNotNull(response);
        assertEquals(user.getId(), response.getId());
        assertEquals(user.getName(), response.getName());
    }

    @Test
    void shouldReturnNullWhenUserNotFound() {
        String search = "nome_inexistente";

        when(userRepository.findByNameContainingIgnoreCaseAndIsDeleted(search, false)).thenReturn(null);

        UserResponse response = tested.search(search);

        verify(userRepository).findByNameContainingIgnoreCaseAndIsDeleted(search, false);
        assertNull(response);
    }
}