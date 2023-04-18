package com.example.recipemanager.repository;

import com.example.recipemanager.model.Recipe;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RecipeRepository extends JpaRepository<Recipe, Long>, CustomRecipeRepository{
   
}

