package com.example.recipemanager.integrationTests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import com.example.recipemanager.dto.IngredientDTO;
import com.example.recipemanager.dto.RecipeDTO;
import com.example.recipemanager.model.Recipe;
import com.example.recipemanager.repository.RecipeRepository;
import com.example.recipemanager.service.RecipeService;

import jakarta.transaction.Transactional;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc(addFilters = false)
@Transactional
@ActiveProfiles("test")
public class RecipeServiceIntegrationTest {

    @Autowired
    private RecipeService recipeService;

    @Autowired
    private RecipeRepository recipeRepository;

    @Test
    public void testCreateRecipe() {
        RecipeDTO recipeDTO = new RecipeDTO();
        recipeDTO.setName("New Recipe");
        recipeDTO.setInstructions("New Instructions");
        recipeDTO.setServings(4);
        recipeDTO.setVegetarian(true);
        List<IngredientDTO> ingredients = new ArrayList<>();
        ingredients.add(new IngredientDTO("ingredient1"));
        ingredients.add(new IngredientDTO("ingredient2"));
        recipeDTO.setIngredients(ingredients);

        Recipe createdRecipe = recipeService.createRecipe(recipeDTO);

        assertNotNull(createdRecipe.getId());
        assertEquals(recipeDTO.getName(), createdRecipe.getName());
        assertEquals(recipeDTO.getInstructions(), createdRecipe.getInstructions());
        assertEquals(recipeDTO.getServings(), createdRecipe.getServings());
        assertEquals(recipeDTO.isVegetarian(), createdRecipe.isVegetarian());
        assertEquals(recipeDTO.getIngredients().size(), createdRecipe.getIngredients().size());
        assertTrue(createdRecipe.getIngredients().containsAll(recipeDTO.getIngredients()));
    }

    @Test
    public void testUpdateRecipe() {
        RecipeDTO recipeDTO = new RecipeDTO();
        recipeDTO.setName("New Recipe");
        recipeDTO.setInstructions("New Instructions");
        recipeDTO.setServings(4);
        recipeDTO.setVegetarian(true);
        List<IngredientDTO> ingredients = new ArrayList<>();
        ingredients.add(new IngredientDTO("ingredient1"));
        ingredients.add(new IngredientDTO("ingredient2"));
        recipeDTO.setIngredients(ingredients);

        Recipe createdRecipe = recipeService.createRecipe(recipeDTO);

        RecipeDTO updatedRecipeDTO = new RecipeDTO();
        updatedRecipeDTO.setName("Updated Recipe");
        updatedRecipeDTO.setInstructions("Updated Instructions");
        updatedRecipeDTO.setServings(6);
        updatedRecipeDTO.setVegetarian(false);
        List<IngredientDTO> updatedIngredients = new ArrayList<>();
        updatedIngredients.add(new IngredientDTO("ingredient3"));
        updatedIngredients.add(new IngredientDTO("ingredient4"));
        recipeDTO.setIngredients(updatedIngredients);

        Recipe updatedRecipe = recipeService.updateRecipe(createdRecipe.getId(), updatedRecipeDTO);

        assertEquals(createdRecipe.getId(), updatedRecipe.getId());
        assertEquals(updatedRecipeDTO.getName(), updatedRecipe.getName());
        assertEquals(updatedRecipeDTO.getInstructions(), updatedRecipe.getInstructions());
        assertEquals(updatedRecipeDTO.getServings(), updatedRecipe.getServings());
        assertEquals(updatedRecipeDTO.isVegetarian(), updatedRecipe.isVegetarian());
        assertEquals(updatedRecipeDTO.getIngredients().size(), updatedRecipe.getIngredients().size());
        assertTrue(updatedRecipe.getIngredients().containsAll(updatedRecipeDTO.getIngredients()));
    }

    @Test
    public void testDeleteRecipe() {
        RecipeDTO recipeDTO = new RecipeDTO();
        recipeDTO.setName("New Recipe");
        recipeDTO.setInstructions("New Instructions");
        recipeDTO.setServings(4);
        recipeDTO.setVegetarian(true);
        List<IngredientDTO> ingredients = new ArrayList<>();
        ingredients.add(new IngredientDTO("ingredient1"));
        ingredients.add(new IngredientDTO("ingredient2"));
        recipeDTO.setIngredients(ingredients);

        Recipe createdRecipe = recipeService.createRecipe(recipeDTO);

        recipeService.deleteRecipe(createdRecipe.getId());

        Optional<Recipe> deletedRecipe = recipeRepository.findById(createdRecipe.getId());

        assertFalse(deletedRecipe.isPresent());
    }

    @Test
    public void testGetAllRecipes() {
        RecipeDTO recipeDTO1 = new RecipeDTO();
        recipeDTO1.setName("Recipe 1");
        recipeDTO1.setInstructions("Instructions 1");
        recipeDTO1.setServings(2);
        recipeDTO1.setVegetarian(true);
        List<IngredientDTO> ingredients = new ArrayList<>();
        ingredients.add(new IngredientDTO("ingredient1"));
        ingredients.add(new IngredientDTO("ingredient2"));
        recipeDTO1.setIngredients(ingredients);

        RecipeDTO recipeDTO2 = new RecipeDTO();
        recipeDTO2.setName("Recipe 2");
        recipeDTO2.setInstructions("Instructions 2");
        recipeDTO2.setServings(4);
        recipeDTO2.setVegetarian(false);
        recipeDTO1.setIngredients(ingredients);
        recipeService.createRecipe(recipeDTO1);
        recipeService.createRecipe(recipeDTO2);

        List<Recipe> allRecipes = recipeService.getAllRecipes();
        assertEquals(2, allRecipes.size());
}
    
}
