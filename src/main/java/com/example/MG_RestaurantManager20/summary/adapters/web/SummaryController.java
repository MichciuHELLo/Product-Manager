package com.example.MG_RestaurantManager20.summary.adapters.web;

import com.example.MG_RestaurantManager20.product.domain.Product;
import com.example.MG_RestaurantManager20.recipe2.domain.Recipe2;
import com.example.MG_RestaurantManager20.recipe2.service.RecipeService2;
import com.example.MG_RestaurantManager20.summary.service.SummaryService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
@AllArgsConstructor
public class SummaryController {

    private final SummaryService summaryService;

    @GetMapping("summary/recipes/all")
    public List<Recipe2> getAllRecipes(){return summaryService.getAllRecipes();}

    @GetMapping("summary/products/all")
    public List<Product> getAllProducts(){return summaryService.getAllProducts();}
}
