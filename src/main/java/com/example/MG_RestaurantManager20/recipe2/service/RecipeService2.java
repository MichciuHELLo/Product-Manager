package com.example.MG_RestaurantManager20.recipe2.service;

import com.example.MG_RestaurantManager20.recipe2.domain.Recipe2;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public interface RecipeService2 {

    Recipe2 addNewRecipe(Recipe2 recipe);

    Recipe2 getRecipeById(long recipeId);

    List<Recipe2> getAllRecipes();

    Recipe2 updateRecipeById(long recipeId, Recipe2 recipe);

    void deleteRecipeById(long recipeId);

    void deleteAllRecipes();

    void deleteSelectedRecipes(Set<Recipe2> recipes);

}
