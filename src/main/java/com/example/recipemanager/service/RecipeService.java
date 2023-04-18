package com.example.recipemanager.service;

import com.example.recipemanager.dto.RecipeDTO;
import com.example.recipemanager.filter.RecipeFilter;
import com.example.recipemanager.model.Recipe;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

@Service
public interface RecipeService {
    Optional<Recipe> getRecipeById(Long id);
    List<Recipe> getAllRecipes();
    List<Recipe> filterRecipes(RecipeFilter filter);
    Recipe createRecipe(RecipeDTO recipeDTO);
    Recipe updateRecipe(Long id, RecipeDTO recipeDTO);
    void deleteRecipe(Long id);
}

