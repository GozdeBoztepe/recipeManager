package com.example.recipemanager.filter;

import java.util.ArrayList;
import java.util.List;

public class RecipeFilter {
    private boolean isVegetarian;
    private int servings;
    private List<String> includedIngredients;
    private List<String> excludedIngredients;
    private String textSearch;

    public RecipeFilter(boolean isVegetarian, int servings, List<String> includedIngredients,
                        List<String> excludedIngredients, String textSearch) {
        this.isVegetarian = isVegetarian;
        this.servings = servings;
        this.includedIngredients = includedIngredients;
        this.excludedIngredients = excludedIngredients;
        this.textSearch = textSearch;
    }

    public RecipeFilter(){
        includedIngredients = new ArrayList<>();
        excludedIngredients = new ArrayList<>();
    }

    public boolean isVegetarian() {
        return isVegetarian;
    }

    public int getServings() {
        return servings;
    }

    public List<String> getIncludedIngredients() {
        return includedIngredients;
    }

    public List<String> getExcludedIngredients() {
        return excludedIngredients;
    }

    public String getTextSearch() {
        return textSearch;
    }

    public void setIsVegetarian(boolean isVegetarian) {
        this.isVegetarian = isVegetarian;
    }

    public void setServings(int servings) {
        this.servings = servings;
    }

    public void setIncludedIngredients(List<String> includedIngredients) {
        this.includedIngredients = includedIngredients;
    }

    public void setExcludedIngredients(List<String> excludedIngredients) {
        this.excludedIngredients = excludedIngredients;
    }

    public void setTextSearch(String textSearch) {
        this.textSearch = textSearch;
    }

    
}

