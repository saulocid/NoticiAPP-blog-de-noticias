package com.EGG.security1.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.EGG.security1.entities.Opinion;

@Repository
public interface OpinionRepository extends JpaRepository<Opinion,String>{
    
    @Query("SELECT o FROM Opinion o WHERE o.email = :email")
    public Opinion buscarPorEmail(@Param("email") String email);

}
