package com.example.MG_RestaurantManager20.product.service.ports;

import com.example.MG_RestaurantManager20.product.adapters.database.RecipeRepository;
import com.example.MG_RestaurantManager20.product.domain.Recipe;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RecipeService {

    private final RecipeRepository recipeRepository;

    @Autowired
    public RecipeService(RecipeRepository recipeRepository) {
        this.recipeRepository = recipeRepository;
    }

    public List<Recipe> getAllRecipes() {
        return recipeRepository.findAll();
    }

    public void addNewRecipe(Recipe recipe) {
        recipeRepository.save(recipe);
    }

    public void deleteRecipe(long longValue) {
        recipeRepository.deleteById(longValue);
    }

    public void deleteAllRecipes() {
        recipeRepository.deleteAll();
    }
}
