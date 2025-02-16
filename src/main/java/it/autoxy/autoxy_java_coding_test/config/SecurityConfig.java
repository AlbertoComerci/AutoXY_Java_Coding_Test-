package it.autoxy.autoxy_java_coding_test.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import it.autoxy.autoxy_java_coding_test.services.CustomUserDetailsService;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable()) // Disabilita CSRF per testare facilmente con Postman
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/api/automobili", "/api/automobili/**").permitAll() // Endpoint pubblici accessibili senza autenticazione
                .anyRequest().authenticated() // Tutti gli altri richiedono autenticazione
            )
            .formLogin(form -> form.disable()) // Disabilita il login form di default
            .httpBasic(basic -> basic.init(http)); // abilita l'autenticazione Basic (username/password)

        return http.build();
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(customUserDetailsService)
        .passwordEncoder(passwordEncoder);
    }
    
}
