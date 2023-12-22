package com.EGG.security1.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.EGG.security1.entities.Imagen;

@Repository
public interface ImagenRepository extends JpaRepository<Imagen,String>{

    @Query("SELECT i FROM Imagen i WHERE i.nombre = :nombre")
    public Imagen buscarPorNombre(@Param("nombre") String nombre);
    
}
