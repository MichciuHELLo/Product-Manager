package com.example.MG_RestaurantManager20.employee.gui;

import com.example.MG_RestaurantManager20.auth.UserSession;
import com.example.MG_RestaurantManager20.employee.domain.Employee;
import com.example.MG_RestaurantManager20.employee.service.EmployeeService;
import com.example.MG_RestaurantManager20.product.domain.Product;
import com.example.MG_RestaurantManager20.product.service.ProductService;
import com.example.MG_RestaurantManager20.recipe2.domain.Recipe2;
import com.example.MG_RestaurantManager20.recipe2.domain.RequiredProducts;
import com.example.MG_RestaurantManager20.recipe2.service.RecipeService2;
import com.example.MG_RestaurantManager20.user.gui.UserSignInGui;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Route("Work")
@PageTitle("Work")
public class EmployeeWorkGui extends VerticalLayout {

    private final UserSession userSession;

    private final RecipeService2 recipeService;
    private final ProductService productService;
    private final EmployeeService employeeService;

    private final Grid<Recipe2> recipeGrid = new Grid<>(Recipe2.class);
    private final Button orderButton = new Button("Place an order", new Icon(VaadinIcon.PLUS));
    private Map<Recipe2, Double> ordersMap = new HashMap<>();

    private Double counter = 0D;

    public EmployeeWorkGui(UserSession userSession, RecipeService2 recipeService, ProductService productService, EmployeeService employeeService) {
        this.userSession = userSession;
        this.recipeService = recipeService;
        this.productService = productService;
        this.employeeService = employeeService;

//        checkAuthorization();

        if (userSession.checkIfAuthenticated()) {
            setSizeFull();
            configureGrid();
            configureView();

            add(recipeGrid, orderButton);
            updateGrid();

            configureMapOfOrders();

            recipeGrid.addComponentColumn(recipe -> {
                NumberField numberField = new NumberField();
                numberField.setHasControls(false);
                numberField.setValue(0D);
                numberField.setReadOnly(true);

                Button addButton = new Button(new Icon(VaadinIcon.PLUS));
                checkIfEnoughProducts(recipe, 0D, addButton);

                Button removeButton = new Button(new Icon(VaadinIcon.MINUS));

                addButton.addClickListener(event -> {
                    counter = numberField.getValue();
                    counter++;
                    ordersMap.put(recipe, counter);
                    numberField.setValue(counter);
                    setEnabledRemoveButton(removeButton, counter);
                    checkIfEnoughProducts(recipe, counter, addButton);
                });

                removeButton.setEnabled(false);
                removeButton.addClickListener(event -> {
                    counter = numberField.getValue();
                    counter--;
                    ordersMap.put(recipe, counter);
                    numberField.setValue(counter);
                    setEnabledRemoveButton(removeButton, counter);
                    checkIfEnoughProducts(recipe, counter, addButton);
                });

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
                    for (Recipe2 recipe : ordersMap.keySet()) {
                        for (int i = 0; i < recipe.getRequiredProducts().size(); i++) {
                            RequiredProducts requiredProduct = recipe.getRequiredProducts().get(i);
                            Product product = productService.getProductByIdFetch(requiredProduct.getProduct_fk());
                            product.setQuantity(product.getQuantity() - ordersMap.get(recipe) * requiredProduct.getQuantity());
                            productService.updateProduct(product.getId(), product);
                        }
                    }
                    Notification.show("You placed an order!").setPosition(Notification.Position.BOTTOM_CENTER);
                    updateGrid();
                    configureMapOfOrders();
                } else {
                    Notification.show("You have to add at least one dish from the menu.").setPosition(Notification.Position.BOTTOM_CENTER);
                }
            });
        } else {
            UI.getCurrent().navigate(UserSignInGui.class);
            UI.getCurrent().getPage().reload();
        }
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
        Optional<Employee> employee = employeeService.getEmployeeById(userSession.getUserSessionId());
        List<Recipe2> recipes = recipeService.getRecipesByUsersSessionId(employee.get().getUser_fk());
        recipeGrid.setItems(recipes);
    }

    private void configureMapOfOrders() {
        Optional<Employee> employee = employeeService.getEmployeeById(userSession.getUserSessionId());
        List<Recipe2> recipes = recipeService.getRecipesByUsersSessionId(employee.get().getUser_fk());
        for (Recipe2 recipe : recipes) {
            ordersMap.put(recipe, 0D);
        }
    }


    private void checkIfEnoughProducts2() {

        // TODO blokowanie przepisu tylko jednego mimo że produkt skończył się w wielu przepisach

//        for (Recipe2 recipe : ordersMap.keySet()) {
//            Double orders = ordersMap.get(recipe);
//            if (orders != 0) {
//                Double quantity = 0D;
//                for (Recipe2 recipe2 : ordersMap.keySet()) {
//                    for (int i = 0; i < recipe.getRequiredProducts().size(); i++) {
//                        String name = recipe.getRequiredProducts().get(i).getName();
//                        if (recipe2.getRequiredProducts().contains(name)) {
//
//                        }
//                    }
//                }
//            }
//        }

    }


    private void checkIfEnoughProducts(Recipe2 recipe, Double counter, Button button) {
        int count = 0;
        for (int i = 0; i < recipe.getRequiredProducts().size(); i++) {
            Product product = productService.getProductByIdFetch(recipe.getRequiredProducts().get(i).getProduct_fk());
            if (recipe.getRequiredProducts().get(i).getQuantity() * counter >= product.getQuantity() || product.getQuantity() - recipe.getRequiredProducts().get(i).getQuantity() * counter < recipe.getRequiredProducts().get(i).getQuantity()) {
                count++;
            }
        }
        button.setEnabled(count <= 0);
    }

    private void setEnabledRemoveButton(Button removeButton, Double counter) {
        removeButton.setEnabled(counter > 0);
    }

}
