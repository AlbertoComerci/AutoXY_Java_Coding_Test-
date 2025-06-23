package it.autoxy.autoxy_java_coding_test.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
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
        .requestMatchers("/api/utenti/register", "/swagger-ui/**", "/v3/api-docs/**").permitAll() // Endpoints pubblici
        .requestMatchers(HttpMethod.GET, "/api/automobili/**").permitAll() // Anche le GET sono pubbliche
        .anyRequest().authenticated() // Tutto il resto richiede autenticazione (POST, PUT, DELETE auto)
        )
        .httpBasic(Customizer.withDefaults()); // Abilita HTTP Basic Auth invece di disabilitare il form
        
        return http.build();
    }
    
    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(customUserDetailsService)
        .passwordEncoder(passwordEncoder);
    }
    
}
