package com.example.MG_RestaurantManager20.recipe2.adapters.web;

import com.example.MG_RestaurantManager20.recipe2.domain.Recipe2;
import com.example.MG_RestaurantManager20.recipe2.service.RecipeService2;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@AllArgsConstructor
@RequestMapping("/recipe")
public class RecipeController2 {

    private final RecipeService2 recipeService;

    @PostMapping("/add")
    public Recipe2 addNewRecipe(@RequestBody Recipe2 recipe) {
        return recipeService.addNewRecipe(recipe);
    }

    @GetMapping("/{recipeId}")
    public Recipe2 getRecipeById(@PathVariable long recipeId) { return recipeService.getRecipeById(recipeId); }

    @GetMapping("/all")
    public List<Recipe2> getAllRecipes() { return recipeService.getAllRecipes(); }

    @PutMapping("/{recipeId}")
    public Recipe2 updateRecipeById(@PathVariable long recipeId, @RequestBody Recipe2 recipe) { return recipeService.updateRecipeById(recipeId, recipe); }

    @DeleteMapping("/{recipeId}")
    public void deleteRecipeByName(@PathVariable long recipeId) { recipeService.deleteRecipeById(recipeId); }

    @DeleteMapping("/all")
    public void deleteAllRecipes() { recipeService.deleteAllRecipes(); }

}
