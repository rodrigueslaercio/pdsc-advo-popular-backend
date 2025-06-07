package com.ifpe.advopopular.service.usuario;

import com.ifpe.advopopular.repository.usuario.UsuarioRepository;
import com.ifpe.advopopular.models.usuario.Usuario;
import com.ifpe.advopopular.models.usuario.UsuarioDTO;
import com.ifpe.advopopular.service.ServiceGenerico;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UsuarioService extends ServiceGenerico<Usuario, UsuarioRepository> {

    @Autowired
    private PasswordEncoder passwordEncoder;

    public UsuarioService(UsuarioRepository repository) {
        super(repository);
    }

    public Usuario registrarUsuario(UsuarioDTO usuarioDTO) {
        if (emailExiste(usuarioDTO.getEmail())) {
            throw new RuntimeException("Já existe um usuário cadastrado com esse email.");
        }

        if(!confirmaSenha(usuarioDTO.getSenha(), usuarioDTO.getConfirmacaoSenha())) {
            throw new RuntimeException("Senha e Confirmação não coincidem.");
        }

        Usuario usuario = new Usuario();
        usuario.setNome(usuarioDTO.getNome());
        usuario.setEmail(usuarioDTO.getEmail());
        usuario.setSenha(passwordEncoder.encode(usuarioDTO.getSenha()));

        return add(usuario);
    }

    private boolean emailExiste(String email) {
        return repository.existsByEmail(email);
    }

    private boolean confirmaSenha(String senha, String confirmacao) {
        return senha.equals(confirmacao);
    }
}
