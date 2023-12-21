package com.EGG.security1.services;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.EGG.security1.entities.Noticia;
import com.EGG.security1.exceptions.MyException;
import com.EGG.security1.repositories.NoticiaRepository;


@Service
public class NoticiaServices {

    @Autowired
    private NoticiaRepository nr;

    @Transactional
    public void crearNoticia(String titulo, String cuerpo) throws MyException {
        validar(titulo);
        validar(cuerpo);
        Noticia noticia = new Noticia();
        noticia.setTitulo(titulo);
        noticia.setCuerpo(cuerpo);
        nr.save(noticia);
    }

    public List<Noticia> listarNoticias() {
        List<Noticia> noticias = new ArrayList<>();
        noticias = nr.findAll();
        return noticias;
    }

    public Noticia listarNoticia(String id) {
        Noticia noticia = new Noticia();
        noticia = nr.buscarPorId(id);
        return noticia;
    }

    @Transactional
    public void modificarNoticia(Long id, String titulo, String cuerpo) throws MyException {
        validar(titulo);
        validar(cuerpo);
        validarLong(id);
        Optional<Noticia> respuesta = nr.findById(id);
        if (respuesta.isPresent()) {
            Noticia noticia = respuesta.get();
            noticia.setTitulo(titulo);
            noticia.setCuerpo(cuerpo);
            nr.save(noticia);
        }
    }

    @Transactional
    public void eliminarNoticia(Long id) throws MyException {
        validarLong(id);
        try {
            Optional<Noticia> respuesta = nr.findById(id);
            if (respuesta.isPresent()) {
                Noticia noticia = respuesta.get();
                nr.delete(noticia);
            }
        } catch (DataAccessException ex) {
            throw new MyException("Error al eliminar la noticia" + ex.getMessage());
        }
    }

    private void validar(String dato) throws MyException {
        if (dato.isEmpty()) {
            throw new MyException("El dato no puede ser nulo");
        }
    }

    private void validarLong(Long dato) throws MyException {
        if (dato == null) {
            System.out.println("Error");
            throw new MyException("El dato no puede ser nulo");
        }
    }

    public Noticia noticia(Long id){
        Noticia noticia = nr.getReferenceById(id);
        return noticia;
    }

    public int fecha(){
        Calendar fecha = Calendar.getInstance();
        int anio = fecha.get(Calendar.YEAR);
        return anio;
    }
}
