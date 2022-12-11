package com.example.MG_RestaurantManager20.summary.service.impl;

import com.example.MG_RestaurantManager20.product.adapters.database.ProductRepository;
import com.example.MG_RestaurantManager20.product.domain.Product;
import com.example.MG_RestaurantManager20.recipe2.adapters.database.RecipeRepository2;
import com.example.MG_RestaurantManager20.recipe2.domain.Recipe2;
import com.example.MG_RestaurantManager20.summary.service.SummaryService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class SummaryServiceImpl implements SummaryService {

    private final RecipeRepository2 recipeRepository;
    private final ProductRepository productRepository;

    public List<Recipe2> getAllRecipes() {return recipeRepository.findAll();}
    public List<Product> getAllProducts() {return productRepository.findAll();}

}
