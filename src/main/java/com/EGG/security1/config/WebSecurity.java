package com.EGG.security1.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import com.EGG.security1.services.UsuarioServices;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
public class WebSecurity {

        @Autowired
        public UsuarioServices usuarioServices;

        // Encriptado de password
        @Autowired
        public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
                auth.userDetailsService(usuarioServices).passwordEncoder(new BCryptPasswordEncoder());
        }

        // Persimos de usuarios según rol, login y logout
        @Bean
        public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
            http
                .authorizeHttpRequests((authz) ->
                    authz
                        .requestMatchers("/admin/*").hasAnyRole("ADMIN","JOURNAL","MODERATOR") // Solo los usuarios con rol ADMIN pueden acceder a todos los métodos de la clase PanelAdmController.java
                        .anyRequest() // Permitir el acceso a todas las demás rutas 
                        .permitAll() 
                )
                .formLogin((login) ->
                    login
                        .loginPage("/sesion/registro") // GetMapping de registro
                        .loginProcessingUrl("/sesion/logear") // PostMapping de logeo
                        .usernameParameter("email") // con qué variable tomamos al usuario
                        .passwordParameter("password") // con qué contraseña se logea
                        .defaultSuccessUrl("/inicio") // página que se direcciona al logearse
                        .permitAll()
                )
                .logout((logout) -> logout
                    .logoutUrl("/logout") // PostMapping de cerrie de sesión
                    .logoutSuccessUrl("/") // página default cuando se deslogea
                    .permitAll()
                )
                .csrf((csrf) -> csrf.disable());
            return http.build();
        }

}
