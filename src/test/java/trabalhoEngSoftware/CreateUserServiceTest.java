package trabalhoEngSoftware;


import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import trabalhoEngSoftware.controller.request.CreateUserRequest;
import trabalhoEngSoftware.controller.response.IdResponse;
import trabalhoEngSoftware.domain.Users;
import trabalhoEngSoftware.mapper.CreateUserMapper;
import trabalhoEngSoftware.mapper.IdResponseMapper;
import trabalhoEngSoftware.repository.UserRepository;
import trabalhoEngSoftware.service.CreateUserService;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CreateUserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private CreateUserService tested;

    @Captor
    ArgumentCaptor<Users> userCaptor;

    @Test
    @DisplayName("Deve criar usuário com sucesso")
    void deveCriarUsuarioComSucesso() {
        CreateUserRequest request = new CreateUserRequest();
        request.setName("Maria");
        request.setUsername("maria123");
        request.setPassword("12345");

        Users usuario = CreateUserMapper.toEntity(request);
        usuario.setId(10L);
        usuario.setUsername("maria123");
        usuario.setPassword("12345");

        when(userRepository.save(any(Users.class))).thenReturn(usuario);

        IdResponse response = tested.create(request);

        verify(userRepository).save(userCaptor.capture());
        Users salvo = userCaptor.getValue();

        assertEquals("Maria", salvo.getName());
        assertEquals("maria123", salvo.getUsername());
        assertEquals("12345", salvo.getPassword());
    }
}