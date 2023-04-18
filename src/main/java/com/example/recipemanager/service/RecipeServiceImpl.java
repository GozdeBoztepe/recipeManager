package com.example.recipemanager.service;

import com.example.recipemanager.dto.IngredientDTO;
import com.example.recipemanager.dto.RecipeDTO;
import com.example.recipemanager.filter.RecipeFilter;
import com.example.recipemanager.model.Ingredient;
import com.example.recipemanager.model.Recipe;
import com.example.recipemanager.repository.RecipeRepository;

import jakarta.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class RecipeServiceImpl implements RecipeService {

    @Autowired
    private final RecipeRepository recipeRepository;

    @Autowired
    public RecipeServiceImpl(RecipeRepository recipeRepository) {
        this.recipeRepository = recipeRepository;
    }

    @Override
    public Optional<Recipe> getRecipeById(Long id) {
        Optional<Recipe> recipe = recipeRepository.findById(id);
        return recipe;
    }

    @Override
    public List<Recipe> getAllRecipes() {
        List<Recipe> recipes = recipeRepository.findAll();
        return recipes.stream().collect(Collectors.toList());
    }

    @Override
    public List<Recipe> filterRecipes(RecipeFilter filter) {
        List<Recipe> recipes = recipeRepository.filterRecipes(filter);
        return recipes.stream().collect(Collectors.toList());
    }

    @Override
    public Recipe createRecipe(RecipeDTO recipeDTO) {
        Recipe recipe = convertToEntity(recipeDTO);
        return recipeRepository.save(recipe);
    }

    @Override
    public Recipe updateRecipe(Long id, RecipeDTO recipeDTO) {
        Recipe existingRecipe = recipeRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Recipe with id " + id + " not found"));

        Recipe updatedRecipe = updateRecipeData(existingRecipe, recipeDTO);
        return recipeRepository.save(updatedRecipe);
    }

    @Override
    public void deleteRecipe(Long id) {
        Recipe existingRecipe = recipeRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Recipe with id " + id + " not found"));
        recipeRepository.delete(existingRecipe);
    }

    private Recipe convertToEntity(RecipeDTO dto) {
        Recipe recipe = new Recipe();
        recipe.setName(dto.getName());
        recipe.setInstructions(dto.getInstructions());
        recipe.setServings(dto.getServings());
        recipe.setVegetarian(dto.isVegetarian());
        recipe.setIngredients(dto.getIngredients().stream()
                .map(this::convertToEntity)
                .collect(Collectors.toList()));
        return recipe;
    }

    private Ingredient convertToEntity(IngredientDTO dto) {
        Ingredient ingredient = new Ingredient();
        ingredient.setName(dto.getName());
        return ingredient;
    }

    private Recipe updateRecipeData(Recipe recipe, RecipeDTO recipeDTO) {
        recipe.setName(recipeDTO.getName());
        recipe.setInstructions(recipeDTO.getInstructions());
        recipe.setVegetarian(recipeDTO.isVegetarian());
        recipe.setServings(recipeDTO.getServings());
        recipe.setIngredients(recipeDTO.getIngredients().stream()
                .map(this::convertToEntity)
                .collect(Collectors.toList()));
        return recipe;
    }

}