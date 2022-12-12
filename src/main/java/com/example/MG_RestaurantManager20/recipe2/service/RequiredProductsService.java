package com.example.MG_RestaurantManager20.recipe2.service;

import com.example.MG_RestaurantManager20.recipe2.domain.RequiredProducts;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public interface RequiredProductsService {

    List<RequiredProducts> getAllRequiredProducts();

    List<RequiredProducts> getAllRequiredProductsByRecipeId(Long recipeId);

    RequiredProducts addRequiredProductToRecipe(Long recipeId, Long productId, Double quantity);

    void deleteSelectedRequiredProducts(Set<RequiredProducts> requiredProducts);

}
