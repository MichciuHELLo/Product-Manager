package com.example.MG_RestaurantManager20.employee.gui;

import com.example.MG_RestaurantManager20.recipe2.domain.Recipe2;
import com.example.MG_RestaurantManager20.recipe2.service.RecipeService2;
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

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Route("Work")
@PageTitle("Work")
public class EmployeeWorkGui extends VerticalLayout {

    private final RecipeService2 recipeService;

    private final Grid<Recipe2> recipeGrid = new Grid<>(Recipe2.class);
    private final Button orderButton = new Button("Place an order", new Icon(VaadinIcon.PLUS));
    private Map<String, Double> ordersMap = new HashMap<>();

    private Double counter = 0D;

    public EmployeeWorkGui(RecipeService2 recipeService) {
        this.recipeService = recipeService;

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
                // TODO sprawdzić czy jest wystarczająca ilość produktów do zrobienia dania
                counter = numberField.getValue();
                counter++;
                ordersMap.put(item.getName(), counter);
                numberField.setValue(counter);
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

            HorizontalLayout v = new HorizontalLayout(numberField, addButton, removeButton);
            v.setSpacing(true);

            return v;
        });

        orderButton.addClickListener(event -> {
            boolean moreThenZeroOrders = false;
            for (Double value : ordersMap.values()) {
                if (value > 0){
                    moreThenZeroOrders = true;
                    break;
                }
            }

            if (moreThenZeroOrders){
                // TODO odjąć wszystkie produkty z DB wchodzące w skład zamówionych potraw
                Notification.show("You placed an order!");
                System.out.println(ordersMap);
                updateGrid();
                configureMapOfOrders();
            }
            else {
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
        List<Recipe2> recipeList = recipeService.getAllRecipes();
        for (Recipe2 recipe : recipeList) {
            ordersMap.put(recipe.getName(), 0D);
        }
    }

    private void setEnabledRemoveButton(Button removeButton, Double counter) {
        removeButton.setEnabled(counter > 0);
    }

}
