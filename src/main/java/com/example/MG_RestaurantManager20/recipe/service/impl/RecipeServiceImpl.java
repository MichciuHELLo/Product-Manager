package com.example.MG_RestaurantManager20.recipe.service.impl;

import com.example.MG_RestaurantManager20.product.struct.ProductStructure;
import com.example.MG_RestaurantManager20.recipe.adapters.database.RecipeRepository;
import com.example.MG_RestaurantManager20.recipe.domain.Recipe;
import com.example.MG_RestaurantManager20.recipe.service.RecipeService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@AllArgsConstructor
public class RecipeServiceImpl implements RecipeService {

    private final RecipeRepository recipeRepository;

    public List<Recipe> getAllRecipes() {
        return recipeRepository.findAll();
    }

    public void addNewRecipe(Recipe recipe) {
        recipeRepository.save(recipe);
    }

    public void deleteRecipe(Long recipeId) {
        recipeRepository.deleteById(recipeId);
    }

    public void deleteAllRecipes() {
        recipeRepository.deleteAll();
    }

    @Transactional
    public void addToRequiredProducts(String recipeName, ProductStructure productStructure) {

        Recipe repoRecipe = recipeRepository.findProductByName(recipeName).orElseThrow(() -> {
            throw new IllegalStateException("No such recipe in data base");
        });


        if (repoRecipe.getRequiredProducts().isBlank()) {
            repoRecipe.setRequiredProducts(productStructure.toString());
        } else {

            repoRecipe.setRequiredProducts(repoRecipe.getRequiredProducts() + productStructure.toString());
        }
    }
}
