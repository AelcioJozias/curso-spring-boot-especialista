package io.github.cursodsousa.libraryapi.controller;

import io.github.cursodsousa.libraryapi.controller.dto.UsuarioDTO;
import io.github.cursodsousa.libraryapi.controller.mappers.UsuarioMapper;
import io.github.cursodsousa.libraryapi.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("usuarios")
@RequiredArgsConstructor
public class UsuarioController {

    private final UsuarioService usuarioService;
    private final UsuarioMapper usuarioMapper;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void salvar(@RequestBody UsuarioDTO usuarioDTO) {
        final var usuario = usuarioMapper.toEntity(usuarioDTO);
        usuarioService.salvar(usuario);
    }
}
