package com.example.MG_RestaurantManager20.product.gui;

import com.example.MG_RestaurantManager20.product.adapters.database.ProductRepository;
import com.example.MG_RestaurantManager20.product.adapters.database.RecipeRepository;
import com.example.MG_RestaurantManager20.product.adapters.web.ProductController;
import com.example.MG_RestaurantManager20.product.adapters.web.RecipeController;
import com.example.MG_RestaurantManager20.product.domain.Product;
import com.example.MG_RestaurantManager20.product.domain.ProductUnit;
import com.example.MG_RestaurantManager20.product.domain.Recipe;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.radiobutton.RadioButtonGroup;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

@Route("Recipes")
public class RecipeGui extends VerticalLayout {


    // ------ Adding visible part ------ //
    private final Grid<Recipe> gridRecipes;

    // Adding fields ---!
    private final TextField textFieldAddName;
    private final TextField textFieldAddDescription;

    // Deleting fields ---!

    private final IntegerField integerFieldDeleteID;
    private final RadioButtonGroup<String> radioButtonGroupDelete;
    private final Checkbox checkboxConfirmationDelete;

    /*

    // Editing fields ---!
    // private final Span spanEdit;
    private final IntegerField integerFieldEditID;
    private final TextField textFieldEditName;
    private final TextField textFieldEditDescription;
    // private Button buttonEditProduct;
*/

    private Notification notification;

    @Autowired
    public RecipeGui(RecipeRepository recipeRepository, RecipeController recipeController) {

        // ------ Setting up the visible part ------ //
        gridRecipes = new Grid<>(Recipe.class);
        gridRecipes.setItems(recipeController.getAllRecipes());
        gridRecipes.setColumns("id", "name", "description");


        // ------------------------ Adding product fields ------------------------ //

        Span spanAdd = new Span();
        textFieldAddName = new TextField("Name:");
        textFieldAddDescription = new TextField("Description:");
        Button buttonAddRecipe = new Button("Add new recipe", new Icon(VaadinIcon.PLUS));

        // ------ Setting up adding fields ( visual ) ------ //
        buttonAddRecipe.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        spanAdd.getElement().setProperty("innerHTML", "<h1>Add recipe zone</h1>");

        var addingLayout = new VerticalLayout(spanAdd, textFieldAddName, textFieldAddDescription, buttonAddRecipe);
        addingLayout.setSizeFull();
        addingLayout.setSpacing(true);
        addingLayout.setAlignItems(Alignment.STRETCH);

        // ------ Action of the adding button ------ //
        buttonAddRecipe.addClickListener(buttonClickEvent -> {
            if (textFieldAddName.isEmpty() || textFieldAddDescription.isEmpty()) {
                notification = new Notification("Fill all fields!", 3000);
                notification.open();
            } else {
                String convertedName = textFieldAddName.getValue().toLowerCase();
                convertedName = convertedName.substring(0, 1).toUpperCase() + convertedName.toLowerCase().substring(1);

                Optional<Recipe> recipeOptional = recipeRepository.findProductByName(convertedName);
                if (recipeOptional.isPresent()) {
                    notification = new Notification("Product \"" + convertedName + "\" already exists!", 3000);
                    notification.open();
                } else {

                    Recipe recipe = new Recipe(convertedName, textFieldAddDescription.getValue());
                    recipeController.addNewRecipe(recipe);

                    gridRecipes.setItems(recipeController.getAllRecipes());

                    notification = new Notification("Recipe \"" + convertedName + "\" added!", 3000);
                    notification.open();

                    textFieldAddName.clear();
                    textFieldAddDescription.clear();
                }
            }
        });

/*
        // ------------------------ Editing product fields ------------------------ //

        Span spanEdit = new Span();
        integerFieldEditID = new IntegerField("ID of product you want to Edit:");
        textFieldEditName = new TextField("New name:");
        numberFieldEditMin = new NumberField("New minimum");
        numberFieldEditQuantity = new NumberField("New quantity");
        comboBoxEditUnit = new ComboBox<>("New unit", ProductUnit.values());
        Button buttonEditProduct = new Button("Edit product", new Icon(VaadinIcon.WRENCH));

        buttonEditProduct.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        integerFieldEditID.setWidthFull();
        integerFieldEditID.setHasControls(true);
        numberFieldEditMin.setWidthFull();
        numberFieldEditMin.setHasControls(true);
        numberFieldEditQuantity.setWidthFull();
        numberFieldEditQuantity.setHasControls(true);
        spanEdit.getElement().setProperty("innerHTML", "<h1>Editing zone</h1>");

        // ------ Setting up editing fields ( visual ) ------ //
        var editingLayout = new VerticalLayout(spanEdit, integerFieldEditID, textFieldEditName, numberFieldEditMin, numberFieldEditQuantity, comboBoxEditUnit, buttonEditProduct);
        editingLayout.setSizeFull();
        editingLayout.setSpacing(true);
        editingLayout.setAlignItems(Alignment.STRETCH);

        // ------ Action of the editing button ------ //
        buttonEditProduct.addClickListener(buttonClickEvent -> {
            if (integerFieldEditID.isEmpty()) {
                notification = new Notification("You have to fill ID box!", 3000);
                notification.open();
            } else {
                boolean exists = productRepository.existsById(integerFieldEditID.getValue().longValue());
                if (!exists) {
                    notification = new Notification("Product with this ID: '" + integerFieldEditID.getValue() + "' doesn't exists!", 3000);
                    notification.open();
                } else {
                    if (numberFieldEditQuantity.getValue() != null && numberFieldEditQuantity.getValue() < 0 || numberFieldEditMin.getValue() != null && numberFieldEditMin.getValue() < 0) {
                        notification = new Notification("New Minimum and New Quantity can't be less then 0!", 3000);
                        notification.open();
                    } else {
                        String convertedName = textFieldEditName.getValue();
                        if (!textFieldEditName.isEmpty()) {
                            convertedName = textFieldEditName.getValue().toLowerCase();
                            convertedName = convertedName.substring(0, 1).toUpperCase() + convertedName.toLowerCase().substring(1);
                        }
                        Product product = new Product(convertedName, numberFieldEditMin.getValue(), numberFieldEditQuantity.getValue(), comboBoxEditUnit.getValue());
                        productController.updateProduct(integerFieldEditID.getValue().longValue(), product);

                        gridProducts.setItems(productController.getProducts());

                        notification = new Notification("Product with ID: '" + integerFieldEditID.getValue() + "' has been changed!", 3000);
                        notification.open();

                        integerFieldEditID.clear();
                        textFieldEditName.clear();
                        numberFieldEditMin.clear();
                        numberFieldEditQuantity.clear();
                        comboBoxEditUnit.clear();
                    }
                }
            }
        });
*/

        // ------------------------ Deleting product fields ------------------------ //

        Span spanDelete = new Span();
        radioButtonGroupDelete = new RadioButtonGroup<>();
        integerFieldDeleteID = new IntegerField("ID of product you want to Delete");
        checkboxConfirmationDelete = new Checkbox();
        Button buttonDeleteProduct = new Button("Delete product", new Icon(VaadinIcon.TRASH));

        radioButtonGroupDelete.setLabel("Do you want to delete one product or all of them?");
        radioButtonGroupDelete.setItems("Delete one", "Delete all");
        integerFieldDeleteID.setHasControls(true);
        integerFieldDeleteID.setEnabled(false);
        integerFieldDeleteID.setWidthFull();
        checkboxConfirmationDelete.setLabel("Are you sure?");
        buttonDeleteProduct.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        spanDelete.getElement().setProperty("innerHTML", "<h1>Delete recipe zone</h1>");

        // ------ Setting up deleting fields ( visual ) ------ //
        var deletingLayout = new VerticalLayout(spanDelete, radioButtonGroupDelete, integerFieldDeleteID, checkboxConfirmationDelete, buttonDeleteProduct);
        deletingLayout.setSizeFull();
        deletingLayout.setSpacing(true);
        deletingLayout.setAlignItems(Alignment.STRETCH);

        radioButtonGroupDelete.addValueChangeListener(event -> {
            if (radioButtonGroupDelete.getValue().equals("Delete one")) {
                integerFieldDeleteID.setEnabled(true);
            } else if (radioButtonGroupDelete.getValue().equals("Delete all")) {
                integerFieldDeleteID.setEnabled(false);
            } else {
                integerFieldDeleteID.setEnabled(false);
            }
        });

        // ------ Action of the deleting button ------ //
        buttonDeleteProduct.addClickListener(buttonClickEvent -> {
            if (radioButtonGroupDelete.getValue() == null) {
                notification = new Notification("You have to choose one option!", 3000);
                notification.open();
            } else {
                if (radioButtonGroupDelete.getValue().equals("Delete one")) {
                    if (integerFieldDeleteID.isEmpty() || checkboxConfirmationDelete.getValue().equals(false)) {
                        notification = new Notification("You have to fill ID box and confirm your choice!", 3000);
                        notification.open();
                    } else {
                        boolean exists = recipeRepository.existsById(integerFieldDeleteID.getValue().longValue());
                        if (!exists) {
                            notification = new Notification("Recipe with this ID: '" + integerFieldDeleteID.getValue() + "' doesn't exists!", 3000);
                            notification.open();
                        } else {
                            recipeController.deleteRecipe(integerFieldDeleteID.getValue().longValue());

                            gridRecipes.setItems(recipeController.getAllRecipes());

                            notification = new Notification("Recipe with this ID: '" + integerFieldDeleteID.getValue() + "' has been deleted!", 3000);
                            notification.open();

                            checkboxConfirmationDelete.setValue(false);
                            checkboxConfirmationDelete.setIndeterminate(false);
                            integerFieldDeleteID.clear();
                            integerFieldDeleteID.setEnabled(false);
                            radioButtonGroupDelete.setValue(null);
                        }
                    }
                } else if (radioButtonGroupDelete.getValue().equals("Delete all")) {
                    if (checkboxConfirmationDelete.getValue().equals(false)) {
                        notification = new Notification("You have to confirm your choice!", 3000);
                        notification.open();
                    } else {
                        recipeController.deleteAllRecipes();

                        gridRecipes.setItems(recipeController.getAllRecipes());

                        notification = new Notification("All products have been deleted!", 3000);
                        notification.open();

                        checkboxConfirmationDelete.setValue(false);
                        checkboxConfirmationDelete.setIndeterminate(false);
                        integerFieldDeleteID.clear();
                        radioButtonGroupDelete.setValue(null);
                    }
                }
            }
        });


        // ------ Printing all the fields ------ //

        var allLayouts = new HorizontalLayout(addingLayout, deletingLayout);//, editingLayout, deletingLayout);
        allLayouts.setWidthFull();
        allLayouts.setSpacing(true);
        allLayouts.setAlignItems(Alignment.BASELINE);

        add(gridRecipes);                      // Grid
        add(allLayouts);                        // All Layouts
    }
}
