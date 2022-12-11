package com.example.MG_RestaurantManager20.summary.service;

import com.example.MG_RestaurantManager20.product.domain.Product;
import com.example.MG_RestaurantManager20.recipe2.domain.Recipe2;

import java.util.List;

public interface SummaryService {
    List<Recipe2> getAllRecipes();
    List<Product> getAllProducts();
}
