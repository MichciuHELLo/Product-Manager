package com.example.MG_RestaurantManager20.recipe.adapters.web;

import com.example.MG_RestaurantManager20.product.struct.ProductStructure;
import com.example.MG_RestaurantManager20.recipe.domain.Recipe;
import com.example.MG_RestaurantManager20.recipe.service.RecipeService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@AllArgsConstructor
public class RecipeController {

    private final RecipeService recipeService;

    @GetMapping("recipes/all")
    public List<Recipe> getAllRecipes() {
        return recipeService.getAllRecipes();
    }

    @GetMapping("recipe/{recipeName}")
    public Optional<Recipe> getRecipeByName(@PathVariable String recipeName) { return recipeService.getRecipeByName(recipeName);}

    @PutMapping("recipes/update/{recipeName}")
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
