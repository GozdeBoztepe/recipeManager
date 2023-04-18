package com.example.recipemanager.repository;

import java.util.Optional;

import com.example.recipemanager.model.User;

public interface CustomUserRepository {
    Optional<User> findByUsername(String username);
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);
}
