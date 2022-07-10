package com.example.MG_RestaurantManager20.recipe.service;

import com.example.MG_RestaurantManager20.product.struct.ProductStructure;
import com.example.MG_RestaurantManager20.recipe.domain.Recipe;

import java.util.List;

public interface RecipeService {

    List<Recipe> getAllRecipes();

    void addToRequiredProducts(String recipeName, ProductStructure productStructure);

    void addNewRecipe(Recipe recipe);

    void deleteRecipe(Long longValue);

    void deleteAllRecipes();

}
