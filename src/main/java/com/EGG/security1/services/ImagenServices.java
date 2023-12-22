package com.EGG.security1.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.EGG.security1.entities.Imagen;
import com.EGG.security1.repositories.ImagenRepository;

import jakarta.transaction.Transactional;

@Service
public class ImagenServices {
    
    @Autowired
    private ImagenRepository ir;

    @Transactional
    public Imagen guardarImagen(MultipartFile archivo){
        if (archivo != null){
            try {
                Imagen imagen = new Imagen();
                imagen.setMime(archivo.getContentType());
                imagen.setNombre(archivo.getName());
                imagen.setContenido(archivo.getBytes());
                return ir.save(imagen);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
        return null;
    }

    @Transactional
    public Imagen actualizarImagen(MultipartFile archivo, String id){
        if (archivo != null){
            try {
                Imagen imagen = new Imagen();
                if (archivo != null) {
                    Optional<Imagen> res = ir.findById(id);
                    if(res.isPresent()){
                        imagen = res.get();
                    }
                }
                imagen.setMime(archivo.getContentType());
                imagen.setNombre(archivo.getName());
                imagen.setContenido(archivo.getBytes());
                return ir.save(imagen);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
        return null;
    }

}
