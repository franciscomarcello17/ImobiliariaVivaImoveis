package com.vivaimoveis.imobiliaria.controller.security;

import com.vivaimoveis.imobiliaria.core.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfiguration {

    @Autowired
    private LoginService loginService;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(loginService);
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        return authenticationProvider;
    }

    @Bean
    public AuthenticationManager authManager(HttpSecurity http) throws Exception {
        return http.getSharedObject(AuthenticationManagerBuilder.class)
                .authenticationProvider(authenticationProvider())
                .build();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .authorizeRequests()
                // Permite o acesso às páginas especificadas
                .requestMatchers("/", "/imovel/detalhes/**", "/cadastro", "/sobre", "/contato", "/contato/erro", "/contato/sucesso", "/contato/enviar", "/uploads/**", "/imovel/comprar", "/imovel/alugar", "/admin/formulario/**").permitAll()
                // Requer autenticação para outras requisições
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage("/login")  // Página de login customizada
                .permitAll()  // Permite o acesso a qualquer usuário
                .defaultSuccessUrl("/", true)  // Página de sucesso após o login
                .and()
                .rememberMe()  // Lembrar o usuário
                .and()
                .logout()
                .invalidateHttpSession(true)  // Invalida a sessão ao sair
                .clearAuthentication(true)  // Limpa as credenciais do usuário
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))  // Configuração do logout
                .logoutSuccessUrl("/")  // Redireciona após logout
                .permitAll();  // Permite logout para todos os usuários

        return http.build();
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return web -> web.debug(false).ignoring().requestMatchers("/css/**", "/js/**", "/images/**", "/imagesimoveis/**", "/lib/**", "/favicon.ico");
    }
}
