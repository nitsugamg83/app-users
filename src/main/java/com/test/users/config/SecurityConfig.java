package com.test.users.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .authorizeRequests()
                .antMatchers("/**", "/home").permitAll()  // Rutas permitidas sin autenticación
                .anyRequest().authenticated()                    // Otras rutas requieren autenticación
            .and()
            .formLogin()   // Opcional: configuración de formulario de inicio de sesión
            .and()
            .httpBasic();  // Opcional: habilita autenticación básica
        return http.build();
    }
}