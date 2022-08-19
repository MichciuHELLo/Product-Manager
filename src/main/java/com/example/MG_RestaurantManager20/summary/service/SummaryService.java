package com.example.MG_RestaurantManager20.summary.service;

import com.example.MG_RestaurantManager20.product.domain.Product;
import com.example.MG_RestaurantManager20.recipe.domain.Recipe;

import java.util.List;

public interface SummaryService {
    List<Recipe> getAllRecipes();
    List<Product> getAllProducts();
}
