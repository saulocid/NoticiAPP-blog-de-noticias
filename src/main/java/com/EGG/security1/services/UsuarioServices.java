package com.EGG.security1.services;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

import com.EGG.security1.entities.Imagen;
import com.EGG.security1.entities.Usuario;
import com.EGG.security1.enums.Rol;
import com.EGG.security1.exceptions.MyException;
import com.EGG.security1.repositories.UsuarioRepository;
import jakarta.servlet.http.HttpSession;
import jakarta.transaction.Transactional;

@Service
public class UsuarioServices implements UserDetailsService {

    @Autowired
    private UsuarioRepository userRepo;

    @Autowired
    private ImagenServices is;

    // creamos el usuario UserDetails para adjuntarle los permisos
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        Usuario usuario = userRepo.buscarPorEmail(email);

        if (usuario != null) {

            // cargamos los permisos en la lista 'permisos'
            List<GrantedAuthority> permisos = new ArrayList<>();
            GrantedAuthority p = new SimpleGrantedAuthority("ROLE_" + usuario.getRol().toString());
            permisos.add(p);

            // setea la configutacion del usuario ya logeado con el usuario actual
            ServletRequestAttributes atrib = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
            HttpSession sesion = atrib.getRequest().getSession();
            sesion.setAttribute("usuarioSesion", usuario);

            return new User(usuario.getEmail(), usuario.getPassword(), permisos);
        } else {
            return null;
        }

    }

    // registro de usuario
    @Transactional
    public void registrarUsuario(MultipartFile archivo, String userName, String email, String password,
            String password2) throws MyException {

        // antes de registrar el usuario validamos los inputs
        validarUsuario(userName, email, password, password2);

        // creamos usuario y seteamos atriburtos
        Usuario usuario = new Usuario();
        usuario.setUsername(userName);
        usuario.setEmail(email);

        // encriptamos la constraseña antes de setearla
        usuario.setPassword(new BCryptPasswordEncoder().encode(password));

        // damos el rol USER por defecto
        usuario.setRol(Rol.USER);

        // creamos y seteamos la imagen
        Imagen imagen = is.guardarImagen(archivo);
        usuario.setImagen(imagen);

        // persistimos en el repositorio hacia la base de datos
        userRepo.save(usuario);
    }

    // validacion de datos de usuario, si no se logra se lanza el error por pantalla
    public void validarUsuario(String usuario, String email, String password, String password2) throws MyException {

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
        List<Usuario> usuarios = listarUsuarios();
        if (usuarios != null) {
            for (Usuario user : usuarios) {

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
    }

    // validamos que el usuario exista y que la contraseña sea igual a la base de
    // datos
    public void validarLogin(String email, String password) throws MyException {

        // validar usuario
        Usuario usuario = userRepo.buscarPorEmail(email);
        if (usuario == null) {
            throw new MyException("El usuario no existe");
        }

        // validar contraseña
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        if (!passwordEncoder.matches(password, usuario.getPassword())) {
            throw new MyException("La contraseña es incorrecta");
        }

        // enviar error al controlador

    }

    public List<Usuario> listarUsuarios() {
        return userRepo.findAll();
    }

    public Usuario buscarUsuario(String id) {
        return userRepo.buscarPorId(id);
    }

    @Transactional
    public void cambiarRol(String id, Rol opc) throws MyException {
        validarRol(opc);
        validarStr(id);
        Usuario usuario = userRepo.buscarPorId(id);
        usuario.setRol(opc);
        userRepo.save(usuario);
    }

    @Transactional
    public void eliminarUsuario(String id) throws MyException {
        validarStr(id);
        Usuario usuario = userRepo.buscarPorId(id);
        userRepo.delete(usuario);
    }

    @Transactional
    public void cambiarImagen(MultipartFile archivo, String id) throws MyException {
        validarStr(id);
        String idImagen = null;
        Usuario usuario = userRepo.buscarPorId(id);
        try {
            if (usuario != null) {
                idImagen = usuario.getImagen().getId();
            }
            Imagen imagen = is.actualizarImagen(archivo, idImagen);
            usuario.setImagen(imagen);
            userRepo.save(usuario);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void validarStr(String dato) throws MyException {
        if (dato.isEmpty()) {
            throw new MyException("El dato no puede ser nulo");
        }
    }

    public void validarRol(Rol dato) throws MyException {
        if (dato == null) {
            throw new MyException("El dato no puede ser nulo");
        }
    }

}