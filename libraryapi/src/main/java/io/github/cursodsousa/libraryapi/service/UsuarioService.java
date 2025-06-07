package io.github.cursodsousa.libraryapi.service;

import io.github.cursodsousa.libraryapi.model.Usuario;
import io.github.cursodsousa.libraryapi.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;


    @Transactional
    public void salvar(Usuario usuario) {
        final var senha = usuario.getSenha();
        usuario.setSenha(passwordEncoder.encode(senha));
        usuarioRepository.save(usuario);
    }

    public Usuario obterPorLogin(String login) {
        return usuarioRepository.findByLogin(login);
    }
}
