package com.example.MG_RestaurantManager20.product.adapters.web;

import com.example.MG_RestaurantManager20.product.domain.Product;
import com.example.MG_RestaurantManager20.product.domain.Recipe;
import com.example.MG_RestaurantManager20.product.service.ports.RecipeService;
import com.example.MG_RestaurantManager20.product.struct.ProductStructure;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class RecipeController {

    private final RecipeService recipeService;

    @Autowired
    public RecipeController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @GetMapping("/r")
    public List<Recipe> getAllRecipes() {
        return recipeService.getAllRecipes();
    }

    @PutMapping("updateProduct/{recipeName}")
    public void updateRecipeDescription(@PathVariable("recipeName") String recipeName, ProductStructure productStructure) {
        recipeService.updateRecipeDescription(recipeName, productStructure);
    }

    @PostMapping("/addRecipe")
    public void addNewRecipe(@RequestBody Recipe recipe) {
        recipeService.addNewRecipe(recipe);
    }

    @DeleteMapping("/deleteRecipes/{recipeId}")
    public void deleteRecipe(@PathVariable("recipeId") Long longValue) {
        recipeService.deleteRecipe(longValue);
    }

    @DeleteMapping("/deleteRecipes")
    public void deleteAllRecipes() {
        recipeService.deleteAllRecipes();
    }
}
