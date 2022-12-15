package com.example.MG_RestaurantManager20.recipe2.service.impl;

import com.example.MG_RestaurantManager20.recipe2.adapters.database.RecipeRepository2;
import com.example.MG_RestaurantManager20.recipe2.domain.Recipe2;
import com.example.MG_RestaurantManager20.recipe2.service.RecipeService2;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class RecipeServiceImpl2 implements RecipeService2 {

    private final RecipeRepository2 recipeRepository;

    @Override
    public Recipe2 addNewRecipe(Recipe2 recipe) {
        return recipeRepository.save(recipe);
    }

    @Override
    public Recipe2 getRecipeById(long recipeId) {
        return recipeRepository.getById(recipeId);
    }

    @Override
    public List<Recipe2> getRecipesByUsersSessionId(Long recipeId) {
        return recipeRepository.getRecipesByUsersSessionId(recipeId);
    }

    @Override
    public List<Recipe2> getAllRecipes() {
        return recipeRepository.findAll();
    }

    @Override
    @Transactional
    public Recipe2 updateRecipeById(long recipeId, Recipe2 recipe) {
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
    public void deleteSelectedRecipes(Set<Recipe2> recipes) {
        var idList = recipes.stream()
                .map(Recipe2::getId).collect(Collectors.toList());

        recipeRepository.deleteAllById(idList);
    }

}
