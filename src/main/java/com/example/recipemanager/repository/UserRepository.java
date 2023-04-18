package com.example.recipemanager.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.recipemanager.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long>, CustomUserRepository {
    
}
