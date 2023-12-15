package com.EGG.noticia.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.EGG.noticia.entities.Administrador;

@Repository
public interface AdministradorRepository extends JpaRepository<Administrador,Long>{
    
}
