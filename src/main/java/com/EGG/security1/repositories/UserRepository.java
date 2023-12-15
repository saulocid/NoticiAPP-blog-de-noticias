package com.egg.security1.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.egg.security1.entities.User;

@Repository
public interface UserRepository extends JpaRepository<User,String> {

   public User findByEmail(String email);

}