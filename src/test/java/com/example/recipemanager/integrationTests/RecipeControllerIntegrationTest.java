package com.example.recipemanager.integrationTests;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.*;

import com.example.recipemanager.dto.IngredientDTO;
import com.example.recipemanager.dto.RecipeDTO;
import com.example.recipemanager.model.Recipe;
import com.example.recipemanager.model.User;
import com.example.recipemanager.repository.UserRepository;
import com.example.recipemanager.security.JwtUtils;
import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@WebAppConfiguration
@ContextConfiguration
@AutoConfigureMockMvc
public class RecipeControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private UserRepository userRepository;
    
    @Autowired
	AuthenticationManager authenticationManager;

    @Autowired
	PasswordEncoder encoder;

	@Autowired
	JwtUtils jwtUtils;

    private String authToken;

    @Before
    public void setup() throws Exception {
        // Create a user and obtain a bearer token for authentication
        User user = new User("user1", 
							 "email",
							 encoder.encode("password"));
		userRepository.save(user);
        Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken("user1", "password"));
		SecurityContextHolder.getContext().setAuthentication(authentication);
		authToken = jwtUtils.generateJwtToken(authentication);
    }

    @Test
    public void testCreateRecipe() throws Exception {
        RecipeDTO recipeDTO = new RecipeDTO();
        recipeDTO.setName("New Recipe");
        recipeDTO.setInstructions("New Instructions");
        recipeDTO.setServings(4);
        recipeDTO.setVegetarian(true);
        List<IngredientDTO> ingredients = new ArrayList<>();
        ingredients.add(new IngredientDTO("ingredient1"));
        ingredients.add(new IngredientDTO("ingredient2"));
        recipeDTO.setIngredients(ingredients);

        mockMvc.perform(post("/recipes")
                .contentType(MediaType.APPLICATION_JSON)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + authToken)
                .content(objectMapper.writeValueAsString(recipeDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").isNumber())
                .andExpect(jsonPath("$.name").value(recipeDTO.getName()))
                .andExpect(jsonPath("$.instructions").value(recipeDTO.getInstructions()))
                .andExpect(jsonPath("$.servings").value(recipeDTO.getServings()))
                .andExpect(jsonPath("$.vegetarian").value(recipeDTO.isVegetarian()))
                .andExpect(jsonPath("$.ingredients", hasSize(recipeDTO.getIngredients().size())))
                .andExpect(jsonPath("$.ingredients", contains(recipeDTO.getIngredients().toArray())));
    }

    @Test
    public void testGetRecipeById() throws Exception {
        RecipeDTO recipeDTO = new RecipeDTO();
        recipeDTO.setName("New Recipe");
        recipeDTO.setInstructions("New Instructions");
        recipeDTO.setServings(4);
        recipeDTO.setVegetarian(true);
        List<IngredientDTO> ingredients = new ArrayList<>();
        ingredients.add(new IngredientDTO("ingredient1"));
        ingredients.add(new IngredientDTO("ingredient2"));
        recipeDTO.setIngredients(ingredients);

        MvcResult result = mockMvc.perform(post("/recipes")
                .contentType(MediaType.APPLICATION_JSON)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + authToken)
                .content(objectMapper.writeValueAsString(recipeDTO)))
                .andExpect(status().isCreated())
                .andReturn();

        String response = result.getResponse().getContentAsString();
        Recipe createdRecipe = objectMapper.readValue(response, Recipe.class);

        mockMvc.perform(get("/recipes/" + createdRecipe.getId())
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + authToken))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(createdRecipe.getId()))
                .andExpect(jsonPath("$.name").value(createdRecipe.getName()))
                .andExpect(jsonPath("$.instructions").value(createdRecipe.getInstructions()))
                .andExpect(jsonPath("$.servings").value(createdRecipe.getServings()))
                .andExpect(jsonPath("$.vegetarian").value(createdRecipe.isVegetarian()))
                .andExpect(jsonPath("$.ingredients", hasSize(createdRecipe.getIngredients().size())))
                .andExpect(jsonPath("$.ingredients", contains(createdRecipe.getIngredients().toArray())));
    }

    @Test
    public void testGetRecipeByIdNotFound() throws Exception {
        mockMvc.perform(get("/recipes/{id}")
        .header(HttpHeaders.AUTHORIZATION, "Bearer " + authToken)
        .param("id", "999"))
        .andExpect(status().isNotFound());
    }

    @Test
    public void testUpdateRecipe() throws Exception {
        RecipeDTO recipeDTO = new RecipeDTO();
        recipeDTO.setName("New Recipe");
        recipeDTO.setInstructions("New Instructions");
        recipeDTO.setServings(4);
        recipeDTO.setVegetarian(true);
        List<IngredientDTO> ingredients = new ArrayList<>();
        ingredients.add(new IngredientDTO("ingredient1"));
        ingredients.add(new IngredientDTO("ingredient2"));
        recipeDTO.setIngredients(ingredients);
    
        MvcResult result = mockMvc.perform(post("/recipes")
                .contentType(MediaType.APPLICATION_JSON)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + authToken)
                .content(objectMapper.writeValueAsString(recipeDTO)))
                .andExpect(status().isCreated())
                .andReturn();
    
        String response = result.getResponse().getContentAsString();
        Recipe createdRecipe = objectMapper.readValue(response, Recipe.class);
    
        RecipeDTO updatedRecipeDTO = new RecipeDTO();
        updatedRecipeDTO.setName("Updated Recipe");
        updatedRecipeDTO.setInstructions("Updated Instructions");
        updatedRecipeDTO.setServings(6);
        updatedRecipeDTO.setVegetarian(false);
        List<IngredientDTO> updatedIngredients = new ArrayList<>();
        updatedIngredients.add(new IngredientDTO("updated ingredient1"));
        updatedIngredients.add(new IngredientDTO("updated ingredient2"));
        recipeDTO.setIngredients(updatedIngredients);
        
        mockMvc.perform(put("/recipes/" + createdRecipe.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + authToken)
                .content(objectMapper.writeValueAsString(updatedRecipeDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(createdRecipe.getId()))
                .andExpect(jsonPath("$.name").value(updatedRecipeDTO.getName()))
                .andExpect(jsonPath("$.instructions").value(updatedRecipeDTO.getInstructions()))
                .andExpect(jsonPath("$.servings").value(updatedRecipeDTO.getServings()))
                .andExpect(jsonPath("$.vegetarian").value(updatedRecipeDTO.isVegetarian()))
                .andExpect(jsonPath("$.ingredients", hasSize(updatedRecipeDTO.getIngredients().size())))
                .andExpect(jsonPath("$.ingredients", contains(updatedRecipeDTO.getIngredients().toArray())));
    }
    
    @Test
    public void testDeleteRecipe() throws Exception {
        RecipeDTO recipeDTO = new RecipeDTO();
        recipeDTO.setName("New Recipe");
        recipeDTO.setInstructions("New Instructions");
        recipeDTO.setServings(4);
        recipeDTO.setVegetarian(true);
        List<IngredientDTO> ingredients = new ArrayList<>();
        ingredients.add(new IngredientDTO("ingredient1"));
        ingredients.add(new IngredientDTO("ingredient2"));
        recipeDTO.setIngredients(ingredients);
            
        MvcResult result = mockMvc.perform(post("/recipes")
                .contentType(MediaType.APPLICATION_JSON)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + authToken)
                .content(objectMapper.writeValueAsString(recipeDTO)))
                .andExpect(status().isCreated())
                .andReturn();
    
        String response = result.getResponse().getContentAsString();
        Recipe createdRecipe = objectMapper.readValue(response, Recipe.class);
    
        mockMvc.perform(delete("/recipes/" + createdRecipe.getId())
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + authToken))
                .andExpect(status().isNoContent());
    
        mockMvc.perform(get("/recipes/" + createdRecipe.getId())
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + authToken))
                .andExpect(status().isNotFound());
    }
    
    @Test
    public void testGetAllRecipes() throws Exception {
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
    
        mockMvc.perform(post("/recipes")
        .contentType(MediaType.APPLICATION_JSON)
        .header(HttpHeaders.AUTHORIZATION, "Bearer " + authToken)
        .content(objectMapper.writeValueAsString(recipeDTO1)))
        .andExpect(status().isCreated());

        mockMvc.perform(post("/recipes")
        .contentType(MediaType.APPLICATION_JSON)
        .header(HttpHeaders.AUTHORIZATION, "Bearer " + authToken)
        .content(objectMapper.writeValueAsString(recipeDTO2)))
        .andExpect(status().isCreated());

        mockMvc.perform(get("/recipes")
        .header(HttpHeaders.AUTHORIZATION, "Bearer " + authToken))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$", hasSize(2)))
        .andExpect(jsonPath("$[0].name").value(recipeDTO1.getName()))
        .andExpect(jsonPath("$[0].instructions").value(recipeDTO1.getInstructions()))
        .andExpect(jsonPath("$[0].servings").value(recipeDTO1.getServings()))
        .andExpect(jsonPath("$[0].vegetarian").value(recipeDTO1.isVegetarian()))
        .andExpect(jsonPath("$[0].ingredients", hasSize(recipeDTO1.getIngredients().size())))
        .andExpect(jsonPath("$[0].ingredients", contains(recipeDTO1.getIngredients().toArray())))
        .andExpect(jsonPath("$[1].name").value(recipeDTO2.getName()))
        .andExpect(jsonPath("$[1].instructions").value(recipeDTO2.getInstructions()))
        .andExpect(jsonPath("$[1].servings").value(recipeDTO2.getServings()))
        .andExpect(jsonPath("$[1].vegetarian").value(recipeDTO2.isVegetarian()))
        .andExpect(jsonPath("$[1].ingredients", hasSize(recipeDTO2.getIngredients().size())))
        .andExpect(jsonPath("$[1].ingredients", contains(recipeDTO2.getIngredients().toArray())));
    }

}

