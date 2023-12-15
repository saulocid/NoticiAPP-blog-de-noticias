package com.EGG.noticia.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.EGG.noticia.entities.Periodista;

@Repository
public interface PeriodistaRepository extends JpaRepository<Periodista,Long>{
    
}
