package com.EGG.noticia.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.EGG.noticia.entities.Usuario;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario,Long>{
    
    @Query("SELECT u FROM Usuario u WHERE u.nombre_usuario = :nombreUsuario")
    public Usuario obtenerPorNombreUsuario(@Param("nombreUsuario") String nombreUsuario);

    @Query("SELECT u FROM Usuario u WHERE u.email = :email")
    public Usuario obtenerPorEmail(@Param("email") String email);

}
