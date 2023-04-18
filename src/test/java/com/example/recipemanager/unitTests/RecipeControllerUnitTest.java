package com.example.recipemanager.unitTests;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.example.recipemanager.controller.RecipeController;
import com.example.recipemanager.dto.RecipeDTO;
import com.example.recipemanager.filter.RecipeFilter;
import com.example.recipemanager.model.Recipe;
import com.example.recipemanager.service.RecipeService;
import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(MockitoJUnitRunner.class)
public class RecipeControllerUnitTest {
    
    @Mock
    private RecipeService recipeService;
    
    @InjectMocks
    private RecipeController recipeController;
    
    private MockMvc mockMvc;
    
    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(recipeController).build();
    }
    
    @Test
    public void testGetRecipe() throws Exception {
        // Arrange
        long recipeId = 1L;
        Recipe recipe = new Recipe("Test Recipe", "Test Instructions", 4, true, Collections.emptyList());
        Mockito.when(recipeService.getRecipeById(recipeId)).thenReturn(Optional.of(recipe));
        
        // Act
        mockMvc.perform(MockMvcRequestBuilders.get("/recipes/" + recipeId))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name", Matchers.is("Test Recipe")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.instructions", Matchers.is("Test Instructions")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.servings", Matchers.is(4)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.vegetarian", Matchers.is(true)));
        
        // Assert
        Mockito.verify(recipeService, Mockito.times(1)).getRecipeById(recipeId);
    }
    
    @Test
    public void testGetAllRecipes() throws Exception {
        // Arrange
        Recipe recipe1 = new Recipe("Test Recipe 1", "Test Instructions 1", 4, true, Collections.emptyList());
        Recipe recipe2 = new Recipe("Test Recipe 2", "Test Instructions 2", 6, false, Collections.emptyList());
        List<Recipe> recipes = Arrays.asList(recipe1, recipe2);
        Mockito.when(recipeService.getAllRecipes()).thenReturn(recipes);
        
        // Act
        mockMvc.perform(MockMvcRequestBuilders.get("/recipes/"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.[0].name", Matchers.is("Test Recipe 1")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.[0].instructions", Matchers.is("Test Instructions 1")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.[0].servings", Matchers.is(4)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.[0].vegetarian", Matchers.is(true)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.[1].name", Matchers.is("Test Recipe 2")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.[1].instructions", Matchers.is("Test Instructions 2")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.[1].servings", Matchers.is(6)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.[1].vegetarian", Matchers.is(false)));
        
        // Assert
        Mockito.verify(recipeService, Mockito.times(1)).getAllRecipes();
    }
    
    // Add more tests for createRecipe(), updateRecipe(), deleteRecipe(), and filterRecipes() methods

    @Test
    public void testGetRecipeByIdNotFound() throws Exception {
        when(recipeService.getRecipeById(1L)).thenReturn(Optional.empty());

        mockMvc.perform(MockMvcRequestBuilders.get("/recipes/1"))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    public void testCreateRecipe() throws Exception {
        RecipeDTO recipeDTO = new RecipeDTO();
        recipeDTO.setName("New Recipe");
        recipeDTO.setInstructions("New Instructions");
        recipeDTO.setServings(4);
        recipeDTO.setVegetarian(true);
        recipeDTO.setIngredients(new ArrayList<>());

        Recipe createdRecipe = new Recipe("New Recipe", "New Instructions", 4, true, new ArrayList<>());
        createdRecipe.setId(1L);
        when(recipeService.createRecipe(recipeDTO)).thenReturn(createdRecipe);

        String requestJson = new ObjectMapper().writeValueAsString(recipeDTO);

        mockMvc.perform(MockMvcRequestBuilders.post("/recipes/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("New Recipe"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.instructions").value("New Instructions"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.servings").value(4))
                .andExpect(MockMvcResultMatchers.jsonPath("$.vegetarian").value(true))
                .andExpect(MockMvcResultMatchers.jsonPath("$.ingredients").isArray());
    }

    @Test
    public void testUpdateRecipe() throws Exception {
        RecipeDTO recipeDTO = new RecipeDTO();
        recipeDTO.setName("Updated Recipe");
        recipeDTO.setInstructions("Updated Instructions");
        recipeDTO.setServings(3);
        recipeDTO.setVegetarian(false);
        recipeDTO.setIngredients(new ArrayList<>());

        Recipe updatedRecipe = new Recipe("Updated Recipe", "Updated Instructions", 3, false, new ArrayList<>());
        updatedRecipe.setId(1L);
        when(recipeService.updateRecipe(1L, recipeDTO)).thenReturn(updatedRecipe);

        String requestJson = new ObjectMapper().writeValueAsString(recipeDTO);

        mockMvc.perform(MockMvcRequestBuilders.put("/recipes/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Updated Recipe"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.instructions").value("Updated Instructions"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.servings").value(3))
                .andExpect(MockMvcResultMatchers.jsonPath("$.vegetarian").value(false))
                .andExpect(MockMvcResultMatchers.jsonPath("$.ingredients").isArray());
    }

    @Test
    public void testFilterRecipes() throws Exception {
    // Arrange
    RecipeFilter filter = new RecipeFilter(true, 0, Collections.<String> emptyList(), Collections.<String> emptyList(), "");


    List<Recipe> filteredRecipes = new ArrayList<>();
    Recipe recipe1 = new Recipe("New Recipe1", "New Instructions", 4, true, new ArrayList<>());
    Recipe recipe2 = new Recipe("New Recipe2", "New Instructions", 4, true, new ArrayList<>());
    filteredRecipes.add(recipe1);
    filteredRecipes.add(recipe2);

    when(recipeService.filterRecipes(any(RecipeFilter.class))).thenReturn(filteredRecipes);

    String requestJson = new ObjectMapper().writeValueAsString(filter);

    // Act & Assert
    mockMvc.perform(MockMvcRequestBuilders.post("/recipes/filter")
            .contentType(MediaType.APPLICATION_JSON)
            .content(requestJson).contentType(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.jsonPath("$").isArray())
            .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(2)))
            .andExpect(MockMvcResultMatchers.jsonPath("$[0].id").value(1))
            .andExpect(MockMvcResultMatchers.jsonPath("$[0].name").value("Recipe 1"))
            .andExpect(MockMvcResultMatchers.jsonPath("$[0].instructions").value("Instructions 1"))
            .andExpect(MockMvcResultMatchers.jsonPath("$[0].servings").value(4))
            .andExpect(MockMvcResultMatchers.jsonPath("$[0].vegetarian").value(true))
            .andExpect(MockMvcResultMatchers.jsonPath("$[0].ingredients").isArray())
            .andExpect(MockMvcResultMatchers.jsonPath("$[0].ingredients").isEmpty())
            .andExpect(MockMvcResultMatchers.jsonPath("$[1].id").value(2))
            .andExpect(MockMvcResultMatchers.jsonPath("$[1].name").value("Recipe 2"))
            .andExpect(MockMvcResultMatchers.jsonPath("$[1].instructions").value("Instructions 2"))
            .andExpect(MockMvcResultMatchers.jsonPath("$[1].servings").value(2))
            .andExpect(MockMvcResultMatchers.jsonPath("$[1].vegetarian").value(true))
            .andExpect(MockMvcResultMatchers.jsonPath("$[1].ingredients").isArray())
            .andExpect(MockMvcResultMatchers.jsonPath("$[1].ingredients").isEmpty());
}
}

