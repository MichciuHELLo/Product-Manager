package com.example.MG_RestaurantManager20.recipe.service;

import com.example.MG_RestaurantManager20.product.struct.ProductStructure;
import com.example.MG_RestaurantManager20.recipe.domain.Recipe;

import java.util.List;
import java.util.Optional;

public interface RecipeService {

    List<Recipe> getAllRecipes();

    Optional<Recipe> getRecipeByName(String recipeName);

    void addToRequiredProducts(String recipeName, ProductStructure productStructure);

    void addNewRecipe(Recipe recipe);

    void deleteRecipe(Long longValue);

    void deleteAllRecipes();

}
