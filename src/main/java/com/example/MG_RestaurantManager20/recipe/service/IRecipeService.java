package com.example.MG_RestaurantManager20.recipe.service;

import com.example.MG_RestaurantManager20.product.struct.ProductStructure;
import com.example.MG_RestaurantManager20.recipe.domain.Recipe;

public interface IRecipeService {

    void updateRecipeCalories(Recipe recipe, ProductStructure productStructure);

}
