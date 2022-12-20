package com.example.MG_RestaurantManager20.recipe.service;

import com.example.MG_RestaurantManager20.recipe.domain.Recipe;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public interface RecipeService {

    Recipe addNewRecipe(Recipe recipe);

    Recipe getRecipeById(long recipeId);

    List<Recipe> getRecipesByUsersSessionId(Long recipeId);

    List<Recipe> getAllRecipes();

    Recipe updateRecipeById(long recipeId, Recipe recipe);

    void deleteRecipeById(long recipeId);

    void deleteAllRecipes();

    void deleteSelectedRecipes(Set<Recipe> recipes);

}
