package com.example.recipemanager.repository;

import java.util.List;

import com.example.recipemanager.filter.RecipeFilter;
import com.example.recipemanager.model.Recipe;

public interface CustomRecipeRepository {
    List<Recipe> findAll();
    List<Recipe> filterRecipes(RecipeFilter filter);
}
