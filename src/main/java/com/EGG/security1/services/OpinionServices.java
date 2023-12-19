package com.EGG.security1.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import com.EGG.exceptions.MyException;
import com.EGG.security1.entities.Opinion;
import com.EGG.security1.repositories.OpinionRepository;
import jakarta.transaction.Transactional;

@Service
public class OpinionServices {

    @Autowired
    private OpinionRepository or;

    @Transactional
    public void crearOpinion(String id, String email, String mensaje) throws MyException {
        validarStr(email);
        validarStr(mensaje);
        Opinion o = new Opinion();
        o.setId(id);
        o.setEmail(email);
        o.setMensaje(mensaje);
        or.save(o);
    }

    @Transactional
    public void eliminarOpinion(String id) throws MyException {
        validarStr(id);
        try {
            Optional<Opinion> res = or.findById(id);
            if (res.isPresent()) {
                Opinion o = res.get();
                or.delete(o);
            }
        } catch (DataAccessException ex) {
            throw new MyException("Error al eliminar la opinion" + ex.getMessage());
        }
    }

    public List<Opinion> listarOpiniones() {
        return or.findAll();
    }

    public void validarStr(String dato) throws MyException {
        if (dato.isEmpty()) {
            throw new MyException("El dato no puede ser nulo");
        }
    }

}
