package com.ifpe.advopopular.controller.usuario;

import com.ifpe.advopopular.controller.ControllerGenerico;
import com.ifpe.advopopular.models.usuario.Usuario;
import com.ifpe.advopopular.models.usuario.UsuarioDTO;
import com.ifpe.advopopular.service.usuario.UsuarioService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/usuario")
@CrossOrigin(origins = "*")
public class UsuarioController extends ControllerGenerico<Usuario, UsuarioService> {

    @PostMapping("/cadastrar")
    public ResponseEntity<?> cadastrarUsuario(@RequestBody UsuarioDTO dto) {
       try {
           Usuario usuario = service.registrarUsuario(dto);
           return ResponseEntity.status(HttpStatus.OK).body(usuario);
       } catch (Exception e) {
           return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
       }
    }
}
