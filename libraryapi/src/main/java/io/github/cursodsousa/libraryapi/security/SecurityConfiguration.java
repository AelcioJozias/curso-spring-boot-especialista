package io.github.cursodsousa.libraryapi.security;


import io.github.cursodsousa.libraryapi.service.UsuarioService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.core.GrantedAuthorityDefaults;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
// Essa anotação possibilita o uso de anotações de segurança em métodos, como @Secured e @PreAuthorize.
@EnableMethodSecurity(securedEnabled = true, jsr250Enabled = true)
public class SecurityConfiguration {


    /**
     * Aqui basicamente é o core da confiuraçãao.
     * Onde configuramos por exemplo auql autenticação que será utilizada.
     * Também podemos desabilizar coisas ou fazer a parte de autorização de requisições.
     * @param http HttpSecurity
     * @return SecurityFilterChain
     */
    @Bean
    public SecurityFilterChain securityFilterChain(final HttpSecurity http, final LoginSocialSuccessHandler loginSocialSuccessHandler) throws Exception {
        return http
                .csrf(AbstractHttpConfigurer::disable)
                .formLogin(Customizer.withDefaults())
                .httpBasic(Customizer.withDefaults())
                .authorizeHttpRequests(authorize -> {
                    authorize.requestMatchers(HttpMethod.POST,"/usuarios/**").permitAll();

                    authorize.anyRequest().authenticated();
                })
                .oauth2Login(oauth2 -> oauth2.successHandler(loginSocialSuccessHandler))
                .build();
    }

    /**
     * Esse bean é usado para remover o prefixo "ROLE_" dos nomes de autoridade.
     * @return
     */
    @Bean
    public GrantedAuthorityDefaults grantedAuthorityDefaults() {
        return new GrantedAuthorityDefaults("");
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(10);
    }

    /**
     * Aqui basicamente vamos chamar a nossa implementação customizada de UserDetailsService.
     * Essa nossa implementacão vai devolver umma instancia que é um UserDetails.
     * O user detail tem as informações do usuário que será autenticado.
     * Repare que aqui eu Injeto uma classe mesmo e não só uma função.
     * @param usuarioService UsuarioService
     * @return UserDetailsService
     */
//    @Bean desativei esse cara aqui depois de criar o CustomAuthenticationProvider. Agora o login vai acontecer lá
    public UserDetailsService userDetailsService(final UsuarioService usuarioService) {
        return new CustomUserDetailService(usuarioService);
    }
}
