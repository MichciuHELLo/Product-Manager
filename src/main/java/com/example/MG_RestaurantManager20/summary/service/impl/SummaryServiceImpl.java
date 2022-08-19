package com.example.MG_RestaurantManager20.summary.service.impl;

import com.example.MG_RestaurantManager20.product.adapters.database.ProductRepository;
import com.example.MG_RestaurantManager20.product.domain.Product;
import com.example.MG_RestaurantManager20.recipe.adapters.database.RecipeRepository;
import com.example.MG_RestaurantManager20.recipe.domain.Recipe;
import com.example.MG_RestaurantManager20.summary.service.SummaryService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class SummaryServiceImpl implements SummaryService {

    private final RecipeRepository recipeRepository;
    private final ProductRepository productRepository;

    public List<Recipe> getAllRecipes() {return recipeRepository.findAll();}
    public List<Product> getAllProducts() {return productRepository.findAll();}

}
