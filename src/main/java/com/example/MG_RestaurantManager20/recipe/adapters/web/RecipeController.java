package com.example.MG_RestaurantManager20.recipe.adapters.web;

import com.example.MG_RestaurantManager20.recipe.domain.Recipe;
import com.example.MG_RestaurantManager20.recipe.service.RecipeService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@AllArgsConstructor
@RequestMapping("/recipe")
public class RecipeController {

    private final RecipeService recipeService;

    @PostMapping("/add")
    public Recipe addNewRecipe(@RequestBody Recipe recipe) {
        return recipeService.addNewRecipe(recipe);
    }

    @GetMapping("/{recipeId}")
    public Recipe getRecipeById(@PathVariable long recipeId) { return recipeService.getRecipeById(recipeId); }

    @GetMapping("/all")
    public List<Recipe> getAllRecipes() { return recipeService.getAllRecipes(); }

    @PutMapping("/{recipeId}")
    public Recipe updateRecipeById(@PathVariable long recipeId, @RequestBody Recipe recipe) { return recipeService.updateRecipeById(recipeId, recipe); }

    @DeleteMapping("/{recipeId}")
    public void deleteRecipeByName(@PathVariable long recipeId) { recipeService.deleteRecipeById(recipeId); }

    @DeleteMapping("/all")
    public void deleteAllRecipes() { recipeService.deleteAllRecipes(); }

}
