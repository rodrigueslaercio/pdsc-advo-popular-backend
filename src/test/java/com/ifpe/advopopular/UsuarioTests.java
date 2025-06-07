package com.ifpe.advopopular;

import com.ifpe.advopopular.repository.usuario.UsuarioRepository;
import com.ifpe.advopopular.models.usuario.Usuario;
import com.ifpe.advopopular.models.usuario.UsuarioDTO;
import com.ifpe.advopopular.service.usuario.UsuarioService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.util.ReflectionTestUtils;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class UsuarioTests {

    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    private UsuarioService usuarioService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        usuarioService = new UsuarioService(usuarioRepository);
        ReflectionTestUtils.setField(usuarioService, "passwordEncoder", passwordEncoder);
    }

    @Test
    public void testCadastrarUsuario_Success() {
        UsuarioDTO dto = new UsuarioDTO();
        dto.setNome("Test");
        dto.setEmail("test@test.com");
        dto.setSenha("senha");
        dto.setConfirmacaoSenha("senha");

        when(usuarioRepository.existsByEmail(dto.getEmail())).thenReturn(false);
        when(passwordEncoder.encode(anyString())).thenReturn("senhaCodificada");
        when(usuarioRepository.save(any(Usuario.class))).thenAnswer(i -> i.getArgument(0));

        Usuario usuarioSalvo = usuarioService.registrarUsuario(dto);

        verify(usuarioRepository).save(any(Usuario.class));

        assertEquals(dto.getEmail(), usuarioSalvo.getEmail());
        assertEquals("senhaCodificada", usuarioSalvo.getSenha());
    }

    @Test
    public void testCadastrarUsuario_EmailJaExiste() {
        UsuarioDTO dto = new UsuarioDTO();
        dto.setNome("Test");
        dto.setEmail("test@test.com");
        dto.setSenha("senha");
        dto.setConfirmacaoSenha("senha");

        when(usuarioRepository.existsByEmail("test@test.com")).thenReturn(true);

       RuntimeException exception = assertThrows(RuntimeException.class, () -> {
          usuarioService.registrarUsuario(dto);
       });

       assertEquals("Já existe um usuário cadastrado com esse email.", exception.getMessage());
    }

    @Test
    public void testCadastroUsuario_confirmacaoSenhaErrada() {
        UsuarioDTO dto = new UsuarioDTO();
        dto.setNome("Test");
        dto.setEmail("test@test.com");
        dto.setSenha("senha");
        dto.setConfirmacaoSenha("senha123");

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            usuarioService.registrarUsuario(dto);
        });

        assertEquals("Senha e Confirmação não coincidem.", exception.getMessage());
    }
}
