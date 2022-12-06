package com.example.MG_RestaurantManager20.employee.gui;

import com.example.MG_RestaurantManager20.product.domain.Product;
import com.example.MG_RestaurantManager20.product.service.ProductService;
import com.example.MG_RestaurantManager20.recipe.domain.Recipe;
import com.example.MG_RestaurantManager20.recipe.service.RecipeService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import java.util.*;

@Route("Work")
@PageTitle("Work")
public class EmployeeWorkGui extends VerticalLayout {

    private final RecipeService recipeService;
    private final ProductService productService;

    private final Grid<Recipe> recipeGrid = new Grid<>(Recipe.class);
    private final Button orderButton = new Button("Place an order", new Icon(VaadinIcon.PLUS));
    private Map<String, Double> ordersMap = new HashMap<>();

    private Double counter = 0D;

    public EmployeeWorkGui(RecipeService recipeService, ProductService productService) {
        this.recipeService = recipeService;
        this.productService = productService;

        setSizeFull();
        configureGrid();
        configureView();

        add(recipeGrid, orderButton);
        updateGrid();

        configureMapOfOrders();

        recipeGrid.addComponentColumn(item -> {
            NumberField numberField = new NumberField();
            numberField.setHasControls(false);
            numberField.setValue(0D);
            numberField.setReadOnly(true);

            Button addButton = new Button(new Icon(VaadinIcon.PLUS));
            Button removeButton = new Button(new Icon(VaadinIcon.MINUS));

            addButton.addClickListener(event -> {
                counter = numberField.getValue();
                counter++;
                ordersMap.put(item.getName(), counter);
                numberField.setValue(counter);

                // TODO sprawdzić czy jest wystarczająca ilość produktów do zrobienia dania
//                var recipe = recipeService.getRecipeByName(item.getName());
//                if (recipe.isPresent()) {
//                    var productIntegerMap = recipe.get().getProductsWithQuantity();
//                    for (Map.Entry<Product, Integer> entry : productIntegerMap.entrySet()) {
//                        System.out.println(entry.getKey() + ":" + entry.getValue());
//                    }
                boolean isEnough = checkProductQuantity(item.getName(), counter);
                if (!isEnough) {
                    addButton.setEnabled(false);
                }
//                    var productIntegerMap = recipe.get().getProductsWithQuantity();
//                    var productsSet = productIntegerMap.keySet();
//                    Iterator<Product> productIterator = productsSet.iterator();
//                    while(productIterator.hasNext()) {
//                        System.out.println(productIterator.next().getName());
//                    }
//                }
//                else {
//                    throw new IllegalStateException("Recipe not found");
//                }


                setEnabledRemoveButton(removeButton, counter);
                System.out.printf("add: %d %s %s", item.getId(), item.getName(), item.getDescription());
                System.out.println();
            });

            removeButton.setEnabled(false);
            removeButton.addClickListener(event -> {
                counter = numberField.getValue();
                counter--;
                ordersMap.put(item.getName(), counter);
                numberField.setValue(counter);
                setEnabledRemoveButton(removeButton, counter);
                System.out.printf("remove: %d %s %s", item.getId(), item.getName(), item.getDescription());
                System.out.println();
            });

            boolean isEnough = checkProductQuantity(item.getName(), 1D);
            if (!isEnough) {
                addButton.setEnabled(false);
            }

            HorizontalLayout v = new HorizontalLayout(numberField, addButton, removeButton);
            v.setSpacing(true);

            return v;
        });

        orderButton.addClickListener(event -> {
            boolean moreThenZeroOrders = false;
            for (Double value : ordersMap.values()) {
                if (value > 0) {
                    moreThenZeroOrders = true;
                    break;
                }
            }

            if (moreThenZeroOrders) {
                // TODO odjąć wszystkie produkty z DB wchodzące w skład zamówionych potraw
                Notification.show("You placed an order!");
                System.out.println(ordersMap);
                updateGrid();
                configureMapOfOrders();
            } else {
                Notification.show("You have to add at least one dish from the menu.");
            }
        });
    }

    private void configureGrid() {
        recipeGrid.setSelectionMode(Grid.SelectionMode.MULTI);
        recipeGrid.setSizeFull();
        recipeGrid.setColumns("name", "description");
        recipeGrid.getColumns().forEach(col -> col.setAutoWidth(true));
        recipeGrid.addThemeVariants(GridVariant.LUMO_ROW_STRIPES);
    }

    private void configureView() {
        orderButton.setWidthFull();
    }

    private void updateGrid() {
        recipeGrid.setItems(recipeService.getAllRecipes());
    }

    private void configureMapOfOrders() {
        List<Recipe> recipeList = recipeService.getAllRecipes();
        for (Recipe recipe : recipeList) {
            ordersMap.put(recipe.getName(), 0D);
        }
    }

    private void setEnabledRemoveButton(Button removeButton, Double counter) {
        removeButton.setEnabled(counter > 0);
    }

    private boolean checkProductQuantity(String recipeName, double portions) {

        // TODO przetestować

        Optional<Recipe> recipe = recipeService.getRecipeByName(recipeName);
        if (recipe.isPresent()){

            // TODO mapa nie działa
            var map = recipe.get().getProductsWithQuantity();
            for (Map.Entry<Product, Integer> entry : map.entrySet()) {
                System.out.println(entry.getKey().getName() + ": " + entry.getValue());
                Optional<Product> product = productService.getProductByName(entry.getKey().getName());
                if (product.isPresent()) {
                    System.out.println("if: " + product.get().getQuantity() + " >= " + entry.getValue() * portions);
                    return product.get().getQuantity() >= entry.getValue() * portions;
                } else {
                    throw new IllegalStateException("Product not found");
                }
            }
        } else {
            throw new IllegalStateException("Recipe not found");
        }
        return false;
    }
}
