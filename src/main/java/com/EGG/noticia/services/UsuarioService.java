package com.EGG.noticia.services;

import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.EGG.noticia.entities.Usuario;
import com.EGG.noticia.enums.Rol;
import com.EGG.noticia.exceptions.MyException;
import com.EGG.noticia.repositories.UsuarioRepository;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository ur;

    public void crearUsuario(String nombreUsuario, String email, String password) throws MyException {

        validarStr(nombreUsuario);
        validarStr(email);
        validarStr(password);

        Usuario u = new Usuario();

        u.setNombreUsuario(nombreUsuario);
        u.setEmail(email);
        u.setPassword(password);
        u.setFechaAlta(new Date());
        u.setRol(Rol.USER);
        u.setActivo(true);

        ur.save(u);
    }

    public void modificarNombreUsuario(Long id, String nombreUsuario) throws MyException{
        validarStr(nombreUsuario);
        validarLong(id);
        Optional<Usuario> res = ur.findById(id);
        if(res.isPresent()){
            Usuario u = res.get();
            u.setNombreUsuario(nombreUsuario);
            ur.save(u);
        }
    }

    public void modificarEmail(Long id, String email) throws MyException{
        validarStr(email);
        validarLong(id);
        Optional<Usuario> res = ur.findById(id);
        if(res.isPresent()){
            Usuario u = res.get();
            u.setEmail(email);
            ur.save(u);
        }
    }
    
    public void modificarPassword(Long id, String password) throws MyException{
        validarStr(password);
        validarLong(id);
        Optional<Usuario> res = ur.findById(id);
        if(res.isPresent()){
            Usuario u = res.get();
            u.setPassword(password);
            ur.save(u);
        }
    }

    public void eliminarUsuario(Long id) throws MyException{
        validarLong(id);
        Optional<Usuario> res = ur.findById(id);
        if(res.isPresent()){
            Usuario u = res.get();
            ur.delete(u);
        }
    }
    
    public void validarStr(String dato) throws MyException {
        if (dato.isEmpty()) {
            throw new MyException("El dato no puede ser nulo");
        }
    }

    public void validarLong(Long dato) throws MyException{
        if(dato == null){
            throw new MyException("El id no puede ser nulo");
        }
    }

}
