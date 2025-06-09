package io.github.cursodsousa.libraryapi.security;

import io.github.cursodsousa.libraryapi.model.Usuario;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collection;

/**
 * aqui vamos criar um nosso próprio Authentication, que vai ser o nosso CustomAuthentication
 * Da outra maneira que estávamos usando, antes de usar o login com o gooogle. estávamos usando
 * o authentication padrão do Spring Security. Mas agora estamos criando o nosso próprio.
 * Detalhe, antes a gente só implementava o UserDetails. Após isso o proprio spring devolvia uma instancia
 * de Authentication.
 *
 * Agora idependente de como o usuário se autentique, seja com o google, ou com o login e senha,
 * vamos sempre devolver uma instancia de CustomAuthentication.
 *
 *
 *
 * ACABEI DE DESCOBRIR MAIS UM FATOR IMPORTANTE: Quando a gente faz o login social, o google já manda pronto um Authentication deles
 * ou seja, não esse nosso
 */
@Getter
public class CustomAuthentication implements Authentication {

    private final Usuario usuario;

    public CustomAuthentication(final Usuario usuario) {
        this.usuario = usuario;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return usuario.getRoles().stream()
                .map(SimpleGrantedAuthority::new)
                .toList();
    }

    // nao vamos implementar esse, porque aqui ele já vai estar autenticado, ainda vamos ver como validar a senha
    @Override
    public Object getCredentials() {
        return null;
    }

    // aqui posso retornar alguns dados do usuário, exemplo: departamento da empresa,
    // vamos retornar apenas a instancia do objeto usuario.
    @Override
    public Object getDetails() {
        return usuario;
    }

    @Override
    public Object getPrincipal() {
        return usuario;
    }

    @Override
    public boolean isAuthenticated() {
        return true;
    }

    @Override
    public void setAuthenticated(final boolean isAuthenticated) throws IllegalArgumentException {

    }

    @Override
    public String getName() {
        return usuario.getLogin();
    }
}
