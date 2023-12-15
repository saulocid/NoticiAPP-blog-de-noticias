package com.EGG.noticia.configurations;

import com.EGG.noticia.services.UsuarioService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;



@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
public class WebSecurity {

        @Autowired
        public UsuarioService usuarioServices;

        @Autowired
        public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
                auth.userDetailsService(usuarioServices).passwordEncoder(new BCryptPasswordEncoder());
        }

        @Bean
        public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
                http
                                .authorizeHttpRequests((authz) -> authz
                                                .requestMatchers("/**").permitAll())
                                .formLogin((login) -> login
                                                .loginPage("/login")
                                                .usernameParameter("Username"))
                                .logout((logout) -> logout
                                                .logoutUrl("/logout"))
                                .csrf((csrf) -> csrf.disable());
                return http.build();
        }

}
