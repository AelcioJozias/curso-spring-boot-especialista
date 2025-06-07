package io.github.cursodsousa.libraryapi.security;

import io.github.cursodsousa.libraryapi.model.Usuario;
import io.github.cursodsousa.libraryapi.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;


@RequiredArgsConstructor
public class CustomUserDetailService implements UserDetailsService {

    private final UsuarioService usuarioService;

    @Override
    public UserDetails loadUserByUsername(final String username) throws UsernameNotFoundException {
        Usuario usuario = usuarioService.obterPorLogin(username);
        if (usuario == null) {
            throw new UsernameNotFoundException("Usuário não encontrado com o login: " + username);
        }
        final String[] roles = usuario.getRoles().toArray(new String[0]);
        return User.builder()
                .username(usuario.getLogin())
                .password(usuario.getSenha())
                .roles(roles)
                .build();
    }
}
