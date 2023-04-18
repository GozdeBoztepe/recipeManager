package com.example.recipemanager.repository;

import java.util.Optional;


import org.springframework.transaction.annotation.Transactional;

import com.example.recipemanager.model.User;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;

@Transactional
public class UserRepositoryImpl implements CustomUserRepository{

    @PersistenceContext
    private EntityManager entityManager;

    public Optional<User> findByUsername(String username) {
        TypedQuery<User> query = entityManager.createQuery("SELECT u FROM User u WHERE u.username = :username", User.class);
        query.setParameter("username", username);
        return query.getResultList().stream().findFirst();
    }

    public boolean existsByUsername(String username) {
        TypedQuery<Long> query = entityManager.createQuery("SELECT COUNT(u) FROM User u WHERE u.username = :username", Long.class);
        query.setParameter("username", username);
        return query.getSingleResult() > 0;
    }

    public boolean existsByEmail(String email) {
        TypedQuery<Long> query = entityManager.createQuery("SELECT COUNT(u) FROM User u WHERE u.email = :email", Long.class);
        query.setParameter("email", email);
        return query.getSingleResult() > 0;
    }
}



