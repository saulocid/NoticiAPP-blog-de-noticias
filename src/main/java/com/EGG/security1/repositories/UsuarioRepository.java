package com.EGG.security1.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.EGG.security1.entities.Usuario;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario,String> {

   @Query("SELECT u FROM Usuario u WHERE u.email = :email")
   public Usuario buscarPorEmail(@Param("email")String email);

}