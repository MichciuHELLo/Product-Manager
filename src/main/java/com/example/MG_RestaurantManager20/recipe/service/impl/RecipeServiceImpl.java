package com.example.MG_RestaurantManager20.recipe.service.impl;

import com.example.MG_RestaurantManager20.recipe.adapters.database.RecipeRepository;
import com.example.MG_RestaurantManager20.recipe.domain.Recipe;
import com.example.MG_RestaurantManager20.recipe.service.RecipeService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class RecipeServiceImpl implements RecipeService {

    private final RecipeRepository recipeRepository;

    @Override
    public Recipe addNewRecipe(Recipe recipe) {
        return recipeRepository.save(recipe);
    }

    @Override
    public Recipe getRecipeById(long recipeId) {
        return recipeRepository.getById(recipeId);
    }

    @Override
    public List<Recipe> getRecipesByUsersSessionId(Long recipeId) {
        return recipeRepository.getRecipesByUsersSessionId(recipeId);
    }

    @Override
    public List<Recipe> getAllRecipes() {
        return recipeRepository.findAll();
    }

    @Override
    @Transactional
    public Recipe updateRecipeById(long recipeId, Recipe recipe) {
        recipeRepository.findById(recipeId).orElseThrow(() -> {
            throw new IllegalStateException("Recipe with this ID: '" + recipeId + "' doesn't exists.");
        });
        recipeRepository.updateEmployeeById (recipe.getName(), recipe.getDescription(), recipeId);
        return recipe;
    }

    @Override
    public void deleteRecipeById(long recipeId) {
        recipeRepository.deleteById(recipeId);
    }

    @Override
    public void deleteAllRecipes() {
        recipeRepository.deleteAll();
    }

    @Override
    public void deleteSelectedRecipes(Set<Recipe> recipes) {
        var idList = recipes.stream()
                .map(Recipe::getId).collect(Collectors.toList());

        recipeRepository.deleteAllById(idList);
    }

}
