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
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;
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

    // creamos el usuario UserDetails para adjuntarle los permisos
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        User usuario = userRepo.findByEmail(email);

        if (usuario != null) {
            List<GrantedAuthority> permisos = new ArrayList<>();
            GrantedAuthority p = new SimpleGrantedAuthority("ROLE_" + usuario.getRol().toString());
            permisos.add(p);
            ServletRequestAttributes atrib = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
            HttpSession sesion = atrib.getRequest().getSession();
            return new org.springframework.security.core.userdetails.User(usuario.getEmail(), usuario.getPassword(), permisos);
        } else {
            return null;
        }

    }

    // registro de usuario
    @Transactional
    public void registrarUsuario(String usuario, String email, String password, String password2, ModelMap model)
            throws MyException {
        // antes de registrar el usuario validamos los inputs
        validarUsuario(usuario, email, password, password2, model);

        // creamos usuario y seteamos atriburtos
        User u = new User();
        u.setUsername(usuario);
        u.setEmail(email);

        // codificamos la constraseña antes de setearla
        u.setPassword(new BCryptPasswordEncoder().encode(password));

        // damos el rol USER por defecto
        u.setRol(Rol.USER);

        //persistimos en el repositorio hacia la base de datos
        userRepo.save(u);
    }

    // validacion de datos de usuario, si no se logra se lanza el error por pantalla
    public void validarUsuario(String usuario, String email, String password, String password2, ModelMap model)
            throws MyException {

        // validamos que los input no estén vacíos
        if (usuario.isEmpty()) {
            throw new MyException("El usuario no puede ser nulo");
        }

        if (email.isEmpty()) {
            throw new MyException("El email no puede ser nulo");
        }

        // además le decimos que no puede ser menor a 7 caracteres
        if (password.length() < 8) {
            throw new MyException("Las contraseña debe tener mínimo 8 caracteres");
        }

        // validamos que las 2 constraseñas sean iguales
        if (!password.equals(password2)) {
            throw new MyException("Las contraseñas no son iguales");
        }

        // validamos que al menos haya una letra mayúscula
        if (!password.matches(".*[A-Z].*")) {
            throw new MyException("La constraseña debe contener al menos una letra mayúscula");
        }

        // buscamos usuarios si existen y...
        List<User> usuarios = userRepo.findAll();
        if (usuarios != null) {
            for (User user : usuarios) {

                // ...validamos que el usuario no exista
                if (user.getUsername().equals(usuario)) {
                    throw new MyException("El usuario ya existe");
                }

                // ...y que tampoco el mail
                if (user.getEmail().equals(email)) {
                    throw new MyException("El email ya existe");
                }
            }
        }

        // enviamos los posibles errores al modelmap
        if (model.containsAttribute("error")) {
            throw new MyException("Error de validación");
        }
    }

    // validamos que el usuario exista y que la contraseña sea igual a la base de datos
    public void validarLogin(String email, String password, ModelMap model) throws MyException {

        // validar usuario
        User usuario = userRepo.findByEmail(email);
        if (usuario == null) {
            throw new MyException("El usuario no existe");
        }

        // validar contraseña
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        if (!passwordEncoder.matches(password, usuario.getPassword())) {
            throw new MyException("La contraseña es incorrecta");
        }

        // enviar error al controlador
        if (model.containsAttribute("error")) {
            throw new MyException("Error de validación");
        }
    }
}