package com.example.MG_RestaurantManager20.recipe.service;

import com.example.MG_RestaurantManager20.recipe.domain.RequiredProducts;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public interface RequiredProductsService {

    List<RequiredProducts> getAllRequiredProducts();

    List<RequiredProducts> getAllRequiredProductsByRecipeId(Long recipeId);

    RequiredProducts addRequiredProductToRecipe(Long recipeId, Long productId, String name, Double quantity);

    void deleteSelectedRequiredProducts(Set<RequiredProducts> requiredProducts);

}
