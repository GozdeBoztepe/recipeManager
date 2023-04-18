package com.example.recipemanager.unitTests;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.example.recipemanager.filter.RecipeFilter;
import com.example.recipemanager.model.Ingredient;
import com.example.recipemanager.model.Recipe;
import com.example.recipemanager.repository.RecipeRepository;
import com.example.recipemanager.service.RecipeServiceImpl;


@RunWith(MockitoJUnitRunner.class)
public class RecipeServiceUnitTest {
    
    @Mock
    private RecipeRepository recipeRepository;
    
    @InjectMocks
    private RecipeServiceImpl recipeService;

    @Test
    public void testGetAllRecipes() {
        Ingredient ingredient1 = new Ingredient("flour");
        Ingredient ingredient2 = new Ingredient("sugar");
        List<Ingredient> ingredients = new ArrayList<>();
        ingredients.add(ingredient1);
        ingredients.add(ingredient2);
        Recipe recipe1 = new Recipe("TestRecipe1", "test instructions.", 4, true, ingredients);
        Recipe recipe2 = new Recipe("TestRecipe2", "test instructions",6, false, ingredients);
        List<Recipe> recipeList = Arrays.asList(recipe1, recipe2);

        when(recipeRepository.findAll()).thenReturn(recipeList);

        List<Recipe> recipes = recipeService.getAllRecipes();

        assertEquals(2, recipes.size());
        assertEquals(recipeList, recipes);
    }

    @Test
    public void testGetRecipeById() {
        Ingredient ingredient1 = new Ingredient("flour");
        Ingredient ingredient2 = new Ingredient("sugar");
        List<Ingredient> ingredients = new ArrayList<>();
        ingredients.add(ingredient1);
        ingredients.add(ingredient2);

        Recipe recipe = new Recipe("Test", "test", 4, true, ingredients);

        when(recipeRepository.findById(1L)).thenReturn(Optional.of(recipe));

        Optional<Recipe> resultRecipe = recipeService.getRecipeById(1L);

        assertEquals(Optional.of(recipe), resultRecipe);
    }

    @Test
    public void testGetRecipeByIdNotFound() {
        when(recipeRepository.findById(1L)).thenReturn(Optional.empty());

        assertNotNull(recipeService.getRecipeById(1L));
    }

    @Test
    public void testFilterRecipesByVegetarian() {
        RecipeFilter filter = new RecipeFilter(true, 0, Collections.<String> emptyList(), Collections.<String> emptyList(), "");
        List<Recipe> recipes = new ArrayList<>();
        Ingredient ingredient1 = new Ingredient("flour");
        Ingredient ingredient2 = new Ingredient("sugar");
        List<Ingredient> ingredients = new ArrayList<>();
        ingredients.add(ingredient1);
        ingredients.add(ingredient2);
        when(recipeRepository.filterRecipes(filter)).thenReturn(recipes);
        List<Recipe> result = recipeService.filterRecipes(filter);
        assertEquals(recipes, result);
    }

}
