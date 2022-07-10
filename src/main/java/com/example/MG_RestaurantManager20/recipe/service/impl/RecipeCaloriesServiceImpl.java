package com.example.MG_RestaurantManager20.recipe.service.impl;

import com.example.MG_RestaurantManager20.product.struct.ProductStructure;
import com.example.MG_RestaurantManager20.recipe.adapters.database.RecipeRepository;
import com.example.MG_RestaurantManager20.recipe.domain.Recipe;
import com.example.MG_RestaurantManager20.recipe.service.RecipeCaloriesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class RecipeCaloriesServiceImpl implements RecipeCaloriesService {

    @Autowired
    private RecipeRepository recipeRepository;

    @Override
    @Transactional
    public void updateRecipeCalories(Recipe recipe, ProductStructure productStructure) {
        if(productStructure.productQuantity > 0 && productStructure.product.getCalories() > 0) {
            Double currentRecipeCalories = recipeRepository.getRecipeCaloriesById(recipe.getId());
            currentRecipeCalories += recipe.getCalories() + productStructure.productQuantity * productStructure.product.getCalories();
            recipeRepository.updateRecipeCalories(recipe.getId(), currentRecipeCalories);
        }
    }
}
