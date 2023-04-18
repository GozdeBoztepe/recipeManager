package com.example.recipemanager.repository;

import com.example.recipemanager.filter.RecipeFilter;
import com.example.recipemanager.model.Ingredient;
import com.example.recipemanager.model.Recipe;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import jakarta.transaction.Transactional;

import java.util.ArrayList;
import java.util.List;

@Transactional
public class RecipeRepositoryImpl implements CustomRecipeRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Recipe> findAll() {
        TypedQuery<Recipe> query = entityManager.createQuery("SELECT r FROM Recipe r", Recipe.class);
        return query.getResultList();
    }

    @Override
    public List<Recipe> filterRecipes(RecipeFilter filter) {

        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Recipe> query = cb.createQuery(Recipe.class);
        Root<Recipe> root = query.from(Recipe.class);
        List<Predicate> predicates = new ArrayList<>();

        // filter by vegetarian status
        if (filter.isVegetarian()) {
            predicates.add(cb.isTrue(root.get("vegetarian")));
        }

        // filter by serving size
        if (filter.getServings() > 0) {
            predicates.add(cb.equal(root.get("servings"), filter.getServings()));
        }

        // filter by included ingredients
        if (!filter.getIncludedIngredients().isEmpty()) {
            Join<Recipe, Ingredient> ingredientJoin = root.join("ingredients");
            predicates.add(ingredientJoin.get("name").in(filter.getIncludedIngredients()));
        }

        // filter by excluded ingredients
        if (!filter.getExcludedIngredients().isEmpty()) {
            Join<Recipe, Ingredient> ingredientJoin = root.join("ingredients");
            predicates.add(cb.not(ingredientJoin.get("name").in(filter.getExcludedIngredients())));
        }

        // filter by text search
        if (filter.getTextSearch() != null && !filter.getTextSearch().isEmpty()) {
            predicates.add(cb.like(root.get("instructions"), "%" + filter.getTextSearch() + "%"));
        }

        query.where(predicates.toArray(new Predicate[predicates.size()]));

        TypedQuery<Recipe> typedQuery = entityManager.createQuery(query);
        return typedQuery.getResultList();
    }
}

