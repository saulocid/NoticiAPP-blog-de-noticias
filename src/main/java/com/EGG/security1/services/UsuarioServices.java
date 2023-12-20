package com.EGG.security1.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import com.EGG.security1.entities.User;
import com.EGG.security1.enums.Rol;
import com.EGG.security1.exceptions.MyException;
import com.EGG.security1.repositories.UserRepository;
import jakarta.servlet.http.HttpSession;
import jakarta.transaction.Transactional;

@Service
public class UsuarioServices implements UserDetailsService {

    @Autowired
    private UserRepository userRepo;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        User usuario = userRepo.findByEmail(email);

        if (usuario != null) {
            List<GrantedAuthority> permisos = new ArrayList<>();
            GrantedAuthority p = new SimpleGrantedAuthority("ROLE_" + usuario.getRol().toString());
            permisos.add(p);
            ServletRequestAttributes atrib = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
            HttpSession sesion = atrib.getRequest().getSession();
            return new org.springframework.security.core.userdetails.User(usuario.getEmail(), usuario.getPassword(),
                    permisos);
        }
        return null;

    }

    @Transactional
    public void registrarUsuario(String usuario, String email, String password, String password2) throws MyException {
        validarUsuario(usuario, email, password, password2);
        User u = new User();
        u.setUsername(usuario);
        u.setEmail(email);
        u.setPassword(password);
        u.setRol(Rol.USER);
        userRepo.save(u);
    }

    public void validarUsuario(String usuario, String email, String password, String password2) throws MyException {
        if (usuario.isEmpty()) {
            throw new MyException("El usuario no puede ser nulo");
        }
        if (email.isEmpty()) {
            throw new MyException("El email no puede ser nulo");
        }
        if(password.isEmpty() || password2.isEmpty()){
            throw new MyException("La contraseña no peude ser nula");
        }
        if (!password.equals(password2)) {
            throw new MyException("Las contraseñas no son iguales");
        }
        if (password.length() < 8) {
            throw new MyException("Las contraseña debe tener mínimo 8 caracteres");
        }
        if (!password.matches(".*[A-Z].*")){
            throw new MyException("La constraseña debe contener al menos una letra mayúscula");
        }
        // if (!password.matches(".*[^a-zA-Z0-9].*"){
        //     throw new MyException("La constraseña debe contener al menos un caracter");
        // }
        List<User> usuarios = userRepo.findAll();
        if (usuarios != null) {
            for (User user : usuarios) {
                if (user.getUsername().equals(usuario)) {
                    throw new MyException("El usuario ya existe");
                }
                if(user.getEmail().equals(email)){
                    throw new MyException("El email ya existe");
                }
            }
        }
    }
}