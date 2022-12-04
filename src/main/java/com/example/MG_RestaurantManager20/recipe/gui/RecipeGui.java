package com.example.MG_RestaurantManager20.recipe.gui;

import com.example.MG_RestaurantManager20.product.domain.Product;
import com.example.MG_RestaurantManager20.product.service.ProductService;
import com.example.MG_RestaurantManager20.product.struct.ProductStructure;
import com.example.MG_RestaurantManager20.recipe.adapters.database.RecipeRepository;
import com.example.MG_RestaurantManager20.recipe.domain.Recipe;
import com.example.MG_RestaurantManager20.recipe.service.RecipeCaloriesService;
import com.example.MG_RestaurantManager20.recipe.service.RecipeService;
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

    @Autowired
    private RecipeCaloriesService recipeCaloriesService;

    // ------ Adding visible part ------ //
    private final Grid<Recipe> gridRecipes;

    // Adding fields ---!
    private final TextField textFieldAddName;
    private final TextField textFieldAddDescription;

    // Recipe fields ---!
    private final ComboBox<Product> comboBoxAddProductToRecipe;
    private final ComboBox<Recipe> comboBoxAddToRecipe;
    private final NumberField numberFieldAddProdRecQuantity;

    // Deleting fields ---!
    private final IntegerField integerFieldDeleteID;
    private final RadioButtonGroup<String> radioButtonGroupDelete;
    private final Checkbox checkboxConfirmationDelete;

    private Notification notification;

    @Autowired
    public RecipeGui(RecipeRepository recipeRepository, RecipeService recipeService, ProductService productService) {

        // ------ Setting up the visible part ------ //
        gridRecipes = new Grid<>(Recipe.class);
        gridRecipes.setItems(recipeService.getAllRecipes());
        gridRecipes.setColumns("id", "name", "description", "requiredProducts", "calories");

        //region Adding Fields region
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

                Optional<Recipe> recipeOptional = recipeRepository.getRecipeByName(convertedName);
                if (recipeOptional.isPresent()) {
                    notification = new Notification("Product \"" + convertedName + "\" already exists!", 3000);
                    notification.open();
                } else {

                    Recipe recipe = new Recipe(convertedName, textFieldAddDescription.getValue(), 0D);
                    recipeService.addNewRecipe(recipe);

                    gridRecipes.setItems(recipeService.getAllRecipes());

                    notification = new Notification("Recipe \"" + convertedName + "\" added!", 3000);
                    notification.open();

                    textFieldAddName.clear();
                    textFieldAddDescription.clear();
                }
            }
        });
        //endregion

        //region Adding to Required Products
        // ------------------------ Adding to Recipe fields ------------------------ //

        Span spanAddToRecipe = new Span();

        Recipe[] recipeArray = recipeService.getAllRecipes().toArray(new Recipe[0]);
        Product[] productsArray = productService.getProducts().toArray(new Product[0]);

        comboBoxAddToRecipe = new ComboBox<>("Recipe", recipeArray);
        comboBoxAddProductToRecipe = new ComboBox<>("Product", productsArray);
        numberFieldAddProdRecQuantity = new NumberField("Needed product quantity");

        Button buttonAddToRecipe = new Button("Add new product", new Icon(VaadinIcon.PLUS));

        buttonAddToRecipe.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        numberFieldAddProdRecQuantity.setWidthFull();
        numberFieldAddProdRecQuantity.setHasControls(true);

        spanAddToRecipe.getElement().setProperty("innerHTML", "<h1>Add required products</h1>");

        var addingToRecipeLayout = new VerticalLayout(spanAddToRecipe, comboBoxAddToRecipe, comboBoxAddProductToRecipe, numberFieldAddProdRecQuantity, buttonAddToRecipe);
        addingToRecipeLayout.setSizeFull();
        addingToRecipeLayout.setSpacing(true);
        addingToRecipeLayout.setAlignItems(Alignment.STRETCH);

        // ------ Action of the button ------ //
        buttonAddToRecipe.addClickListener(buttonClickEvent -> {
            if (comboBoxAddToRecipe.isEmpty() || comboBoxAddProductToRecipe.isEmpty() || numberFieldAddProdRecQuantity.isEmpty()) {
                notification = new Notification("Fill all fields!", 3000);
                notification.open();
            } else {
                if (numberFieldAddProdRecQuantity.getValue() < 0) {
                    notification = new Notification("Minimum and Quantity can't be less then 0!", 3000);
                    notification.open();
                } else {
                    ProductStructure tempProductStructure = new ProductStructure(comboBoxAddProductToRecipe.getValue(), numberFieldAddProdRecQuantity.getValue(), comboBoxAddProductToRecipe.getValue().getProductUnit());

                    recipeCaloriesService.updateRecipeCalories(comboBoxAddToRecipe.getValue(), tempProductStructure);

                    recipeService.addToRequiredProducts(comboBoxAddToRecipe.getValue().toString(), tempProductStructure);

                    gridRecipes.setItems(recipeService.getAllRecipes());

                    notification = new Notification(tempProductStructure.toString() + "\" added!", 3000);
                    notification.open();
                    comboBoxAddProductToRecipe.clear();
                    numberFieldAddProdRecQuantity.clear();
                }
            }
        });


        //endregion

        //region Deleting Fields region
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
                            recipeService.deleteRecipe(integerFieldDeleteID.getValue().longValue());

                            gridRecipes.setItems(recipeService.getAllRecipes());

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
                        recipeService.deleteAllRecipes();

                        gridRecipes.setItems(recipeService.getAllRecipes());

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
        //endregion

        // ------ Printing all the fields ------ //

        var allLayouts = new HorizontalLayout(addingLayout, addingToRecipeLayout, deletingLayout);//, editingLayout, deletingLayout);
        allLayouts.setWidthFull();
        allLayouts.setSpacing(true);
        allLayouts.setAlignItems(Alignment.BASELINE);

        add(gridRecipes);                      // Grid
        add(allLayouts);                        // All Layouts
    }
}
