package com.example.MG_RestaurantManager20.summary.gui;

import com.example.MG_RestaurantManager20.product.domain.Product;
import com.example.MG_RestaurantManager20.product.service.ProductService;
import com.example.MG_RestaurantManager20.recipe.domain.Recipe;
import com.example.MG_RestaurantManager20.recipe.service.RecipeCaloriesService;
import com.example.MG_RestaurantManager20.recipe.service.RecipeService;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

@Route("Summary")
public class SummaryGui extends VerticalLayout {

    @Autowired
    private RecipeCaloriesService recipeCaloriesService;

    // ------ Adding visible part ------ //
    private final Grid<Recipe> gridRecipes;
    private final Grid<Product> gridProducts;

    private Notification notification;

    private final String COLUMN_ID = "id";
    private final String COLUMN_NAME = "name";
    private final String COLUMN_CALORIES = "calories";

    @Autowired
    public SummaryGui(RecipeService recipeService, ProductService productService) {
        // ------ Setting up the visible part ------ //
        gridProducts = new Grid<>(Product.class);
        gridRecipes = new Grid<>(Recipe.class);

        gridProducts.setItems(productService.getProducts());
        gridRecipes.setItems(recipeService.getAllRecipes());

        gridProducts.setColumns(COLUMN_ID, COLUMN_NAME, "min", "quantity", "productUnit", COLUMN_CALORIES);
        gridRecipes.setColumns(COLUMN_ID, COLUMN_NAME, "description", "requiredProducts", COLUMN_CALORIES);

        add(gridProducts);
        add(gridRecipes);
    }
}
