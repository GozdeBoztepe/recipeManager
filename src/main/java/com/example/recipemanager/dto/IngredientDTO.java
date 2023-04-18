package com.example.recipemanager.dto;

public class IngredientDTO {
    private String name;
    private boolean vegetarian;

    public IngredientDTO() {
    }

    public IngredientDTO(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isVegetarian() {
        return vegetarian;
    }

    public void setVegetarian(boolean vegetarian) {
        this.vegetarian = vegetarian;
    }
}

