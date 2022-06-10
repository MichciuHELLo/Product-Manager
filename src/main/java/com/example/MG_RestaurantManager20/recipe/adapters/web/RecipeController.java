package com.example.MG_RestaurantManager20.recipe.adapters.web;

import com.example.MG_RestaurantManager20.recipe.domain.Recipe;
import com.example.MG_RestaurantManager20.recipe.service.ports.RecipeService;
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

    @GetMapping("recipes/all")
    public List<Recipe> getAllRecipes() {
        return recipeService.getAllRecipes();
    }

    @PutMapping("recipes/update/{recipeName")
    public void addToRequiredProducts(@PathVariable("recipeName") String recipeName, ProductStructure productStructure) {
        recipeService.addToRequiredProducts(recipeName, productStructure);
    }

    @PostMapping("recipes/add")
    public void addNewRecipe(@RequestBody Recipe recipe) {
        recipeService.addNewRecipe(recipe);
    }

    @DeleteMapping("recipes/delete/{recipeId}")
    public void deleteRecipe(@PathVariable("recipeId") Long longValue) {
        recipeService.deleteRecipe(longValue);
    }

    @DeleteMapping("recipes/delete/all")
    public void deleteAllRecipes() {
        recipeService.deleteAllRecipes();
    }
}
