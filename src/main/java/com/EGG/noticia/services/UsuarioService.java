package com.EGG.noticia.services;

import java.util.Date;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import com.EGG.noticia.entities.Usuario;
import com.EGG.noticia.enums.Rol;
import com.EGG.noticia.exceptions.MyException;
import com.EGG.noticia.repositories.UsuarioRepository;
import java.util.ArrayList;
import java.util.List;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import jakarta.servlet.http.HttpSession;
import jakarta.transaction.Transactional;

@Service
@Primary
public class UsuarioService implements UserDetailsService {

    @Autowired
    private UsuarioRepository userRepo;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        Usuario usuario = userRepo.obtenerPorEmail(email);

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

    // @Transactional
    // public void crearUsuario(String nombreUsuario, String email, String password) throws MyException {

    //     validarStr(nombreUsuario);
    //     validarStr(email);
    //     validarStr(password);

    //     Usuario u = new Usuario();

    //     u.setNombreUsuario(nombreUsuario);
    //     u.setEmail(email);
    //     u.setPassword(password);
    //     u.setFechaAlta(new Date());
    //     u.setRol(Rol.USER);
    //     u.setActivo(true);

    //     userRepo.save(u);
    // }

    // @Transactional
    // public void modificarNombreUsuario(Long id, String nombreUsuario) throws MyException {
    //     validarStr(nombreUsuario);
    //     validarLong(id);
    //     Optional<Usuario> res = userRepo.findById(id);
    //     if (res.isPresent()) {
    //         Usuario u = res.get();
    //         u.setNombreUsuario(nombreUsuario);
    //         userRepo.save(u);
    //     }
    // }

    // @Transactional
    // public void modificarEmail(Long id, String email) throws MyException {
    //     validarStr(email);
    //     validarLong(id);
    //     Optional<Usuario> res = userRepo.findById(id);
    //     if (res.isPresent()) {
    //         Usuario u = res.get();
    //         u.setEmail(email);
    //         userRepo.save(u);
    //     }
    // }

    // @Transactional
    // public void modificarPassword(Long id, String password) throws MyException {
    //     validarStr(password);
    //     validarLong(id);
    //     Optional<Usuario> res = userRepo.findById(id);
    //     if (res.isPresent()) {
    //         Usuario u = res.get();
    //         u.setPassword(password);
    //         userRepo.save(u);
    //     }
    // }

    // @Transactional
    // public void eliminarUsuario(Long id) throws MyException {
    //     validarLong(id);
    //     Optional<Usuario> res = userRepo.findById(id);
    //     if (res.isPresent()) {
    //         Usuario u = res.get();
    //         userRepo.delete(u);
    //     }
    // }

    // public void validarStr(String dato) throws MyException {
    //     if (dato.isEmpty()) {
    //         throw new MyException("El dato no puede ser nulo");
    //     }
    // }

    // public void validarLong(Long dato) throws MyException {
    //     if (dato == null) {
    //         throw new MyException("El id no puede ser nulo");
    //     }
    // }

}