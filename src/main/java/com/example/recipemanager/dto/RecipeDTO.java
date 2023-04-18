package com.example.recipemanager.dto;

import java.util.ArrayList;
import java.util.List;

public class RecipeDTO {
    private String name;
    private String instructions;
    private int servings;
    private boolean vegetarian;
    private List<IngredientDTO> ingredients;

    public RecipeDTO() {
        ingredients = new ArrayList<>();
    }

    public RecipeDTO(String name, String instructions, int servings, boolean vegetarian, List<IngredientDTO> ingredients) {
        this.name = name;
        this.instructions = instructions;
        this.servings = servings;
        this.vegetarian = vegetarian;
        this.ingredients = ingredients;
    }

    // Getters and setters for all fields

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getInstructions() {
        return instructions;
    }

    public void setInstructions(String instructions) {
        this.instructions = instructions;
    }

    public int getServings() {
        return servings;
    }

    public void setServings(int servings) {
        this.servings = servings;
    }

    public boolean isVegetarian() {
        return vegetarian;
    }

    public void setVegetarian(boolean vegetarian) {
        this.vegetarian = vegetarian;
    }

    public List<IngredientDTO> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<IngredientDTO> ingredients) {
        this.ingredients = ingredients;
    }
}

