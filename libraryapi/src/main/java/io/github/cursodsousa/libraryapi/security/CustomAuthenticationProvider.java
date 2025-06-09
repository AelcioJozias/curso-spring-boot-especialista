package io.github.cursodsousa.libraryapi.security;

import io.github.cursodsousa.libraryapi.model.Usuario;
import io.github.cursodsousa.libraryapi.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CustomAuthenticationProvider implements AuthenticationProvider {

    private final UsuarioService usuarioService;
    private final PasswordEncoder passwordEncoder;

    @Override
    public Authentication authenticate(final Authentication authentication) throws AuthenticationException {
        final String login = authentication.getName();
        final String senhaDigitada = authentication.getCredentials().toString();
        final Usuario usuario = usuarioService.obterPorLogin(login);
        if (usuario == null) {
            throwErrorUserNotFound();
        }
        final String senhaCriptografada = usuario.getSenha();
        boolean senhasIguais = passwordEncoder.matches(senhaDigitada, senhaCriptografada);
        if (!senhasIguais) {
            throwErrorUserNotFound();
        }
        return new CustomAuthentication(usuario);
    }

    private void throwErrorUserNotFound() {
        throw new UsernameNotFoundException("Usuario ou senha inválidos");
    }

    // Esse método é usado para verificar se o AuthenticationProvider
    // pode lidar com o tipo de autenticação fornecido.
    // Porém não entendi muito bem como isso funciona, pesquisar melhor depois
    // ah, esse UsernamePasswordAuthenticationToken é o que vai ser passado no método
    // public Authentication authenticate(final Authentication authentication
    @Override
    public boolean supports(final Class<?> authentication) {
        return authentication.isAssignableFrom(UsernamePasswordAuthenticationToken.class);
    }

}
