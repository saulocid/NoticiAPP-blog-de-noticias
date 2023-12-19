package com.EGG.security1.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.EGG.security1.entities.User;

@Repository
public interface UserRepository extends JpaRepository<User,String> {

   @Query("SELECT u FROM User u WHERE u.email = :email")
   public User findByEmail(@Param("email")String email);

}