package com.EGG.noticia.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.EGG.noticia.entities.Noticia;

@Repository
public interface NoticiaRepository  extends JpaRepository<Noticia,Long>{
    
    @Query("SELECT n FROM Noticia n WHERE n.id = :id")
    public Noticia buscarPorId(@Param("id") Long id);

}
