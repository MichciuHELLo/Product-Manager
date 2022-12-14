package com.example.MG_RestaurantManager20.recipe2.gui;

import com.example.MG_RestaurantManager20.product.domain.Product;
import com.example.MG_RestaurantManager20.product.service.ProductService;
import com.example.MG_RestaurantManager20.recipe2.domain.Recipe2;
import com.example.MG_RestaurantManager20.recipe2.domain.RequiredProducts;
import com.example.MG_RestaurantManager20.recipe2.service.RecipeService2;
import com.example.MG_RestaurantManager20.recipe2.service.RequiredProductsService;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.grid.editor.Editor;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import java.util.Optional;
import java.util.Set;

import static com.vaadin.flow.component.button.ButtonVariant.LUMO_TERTIARY_INLINE;

@Route("RecipeGui2")
@PageTitle("RecipeGui2")
public class RecipeGui2 extends VerticalLayout {

    private final RecipeService2 recipeService;

    private final Grid<Recipe2> recipeGrid = new Grid<>(Recipe2.class);

    private final Binder<Recipe2> binder = new Binder<>(Recipe2.class);

    private final TextField nameTextField = new TextField("Name");
    private final TextField descriptionTextField = new TextField("Description");
    private final Button addRecipeButton = new Button("Add", new Icon(VaadinIcon.PLUS));

    private final TextField nameEditField = new TextField();
    private final TextField descriptionEditField = new TextField();
    private final Button editProductsButton = new Button("Edit products");

    private final Button deleteRecipeButton = new Button("Delete", new Icon(VaadinIcon.TRASH));

    private final HorizontalLayout horizontalLayoutRecipe =
            new HorizontalLayout(nameTextField, descriptionTextField, addRecipeButton, deleteRecipeButton);


    private final ProductService productService;
    private final RequiredProductsService requiredProductsService;


    private final Grid<RequiredProducts> requiredProductsGrid = new Grid<>(RequiredProducts.class);

    private final ComboBox<Product> nameProductComboBox = new ComboBox("Name");
    private final NumberField quantityProductNumberField = new NumberField("Quantity");
    private final Button addProductButton = new Button("Add", new Icon(VaadinIcon.PLUS));
    private final Button deleteProductButton = new Button("Delete", new Icon(VaadinIcon.TRASH));

    private final Button returnButton = new Button("Return to your recipes", new Icon(VaadinIcon.ARROW_BACKWARD));

    private final HorizontalLayout horizontalLayoutProduct =
            new HorizontalLayout(nameProductComboBox, quantityProductNumberField, addProductButton, deleteProductButton, returnButton);

    public RecipeGui2(RecipeService2 recipeService, RequiredProductsService requiredProductsService, ProductService productService) {
        this.recipeService = recipeService;
        this.requiredProductsService = requiredProductsService;
        this.productService = productService;

        setSizeFull();
        configureGrid();
        configureView();

        add(recipeGrid, horizontalLayoutRecipe, requiredProductsGrid, horizontalLayoutProduct);
        updateRecipeGrid();

        recipeGrid.addSelectionListener(selection -> deleteRecipeButton.setVisible(recipeGrid.getSelectedItems().size() > 0));

        addRecipeButton.addClickListener(addRecipeClickEvent -> {
            if (nameTextField.getValue().isEmpty()) {
                Notification.show("Name can't be empty.");
            } else {
                String name = nameTextField.getValue();
                name = name.substring(0, 1).toUpperCase() + name.substring(1).toLowerCase();

                String description = descriptionTextField.getValue();
                description = description.substring(0, 1).toUpperCase() + description.substring(1).toLowerCase();

                recipeService.addNewRecipe(new Recipe2(name, description));

                nameTextField.clear();
                descriptionTextField.clear();

                updateRecipeGrid();
            }
        });

// SHOW PRODUCTS
        recipeGrid.addComponentColumn(recipe -> {
            Button showProducts = new Button("Show Products");
            showProducts.addClickListener(showProductsClickEvent -> {
                // TODO zaimplementować edycje produktów wchodzących w skład przepisu
                recipeGrid.setVisible(false);
                horizontalLayoutRecipe.setVisible(false);

                updateRequiredProductsGrid(recipe.getId());

                requiredProductsGrid.addSelectionListener(selection -> deleteProductButton.setVisible(requiredProductsGrid.getSelectedItems().size() > 0));

                requiredProductsGrid.setVisible(true);
                horizontalLayoutProduct.setVisible(true);
                returnButton.setVisible(true);

                addProductButton.addClickListener(addProductClickEvent -> {
                    if (nameProductComboBox.isEmpty() || quantityProductNumberField.isEmpty()) {
                        Notification.show("Fill all the fields to add new product to your recipe.");
                    }
                    else {
                        requiredProductsService.addRequiredProductToRecipe(recipe.getId(), nameProductComboBox.getValue().getId(), quantityProductNumberField.getValue());
                        updateRequiredProductsGrid(recipe.getId());
                    }
                });

                deleteProductButton.addClickListener(deleteProductClickEvent -> {
                    if (requiredProductsGrid.getSelectedItems().size() > 0) {
                        Notification notification = createAcceptNotificationForProduct(requiredProductsGrid.getSelectedItems(), recipe.getId());
                        notification.open();
                    }
                });

                // TODO przesunąć ten guzik na prawo
                // TODO jak jest za dużo produktów potrzebnych w przepisie to rozszerza się grid. Nawet jeśli się usunie produkty to za szeroki grid zostaje
                returnButton.addClickListener(returnClickEvent -> {
                    requiredProductsGrid.setVisible(false);
                    horizontalLayoutProduct.setVisible(false);

                    recipeGrid.setVisible(true);
                    horizontalLayoutRecipe.setVisible(true);
                    updateRecipeGrid();
                });
            });

            return showProducts;
        });
// END SHOW PRODUCTS


//EDIT
        Editor<Recipe2> editor = recipeGrid.getEditor();
        Grid.Column<Recipe2> editColumn = recipeGrid.addComponentColumn(recipe -> {
            Button editButton = new Button("Edit");
            editButton.addClickListener(editClickEvent -> {
                editor.editItem(recipe);
            });
            editButton.setEnabled(!editor.isOpen());
            return editButton;
        });
        editor.setBinder(binder);
        editor.setBuffered(true);

        createEditFields();

        Button saveButton = new Button("Save", e -> {
            if (!(editor.getItem().getName().equals(nameEditField.getValue()) && editor.getItem().getDescription().equals(descriptionEditField.getValue())
                    || nameEditField.getValue().isEmpty() || descriptionEditField.getValue().isEmpty())) {
                recipeService.updateRecipeById(editor.getItem().getId(), new Recipe2(nameEditField.getValue(), descriptionEditField.getValue(), editor.getItem().getRequiredProducts()));
                editor.save();
            }
            updateRecipeGrid();
        });
        Button cancelButton = new Button(VaadinIcon.CLOSE.create(), e -> editor.cancel());
        cancelButton.addThemeVariants(ButtonVariant.LUMO_ICON, ButtonVariant.LUMO_ERROR);
        HorizontalLayout actions = new HorizontalLayout(saveButton, cancelButton);
        actions.setPadding(false);
        editColumn.setEditorComponent(actions);
//EDIT

        deleteRecipeButton.addClickListener(deleteRecipeClickEvent -> {
            if (recipeGrid.getSelectedItems().size() > 0) {
                Notification notification = createAcceptNotificationForRecipe(recipeGrid.getSelectedItems());
                notification.open();
            }
        });
    }

    private void configureView() {
        recipeGrid.setSelectionMode(Grid.SelectionMode.MULTI);
        recipeGrid.setSizeFull();
        recipeGrid.setColumns("name", "description");
        recipeGrid.getColumns().forEach(col -> col.setAutoWidth(true));
        recipeGrid.addThemeVariants(GridVariant.LUMO_ROW_STRIPES);

        requiredProductsGrid.setSelectionMode(Grid.SelectionMode.MULTI);
        requiredProductsGrid.setSizeFull();
        requiredProductsGrid.setColumns("name", "quantity");
        requiredProductsGrid.getColumns().forEach(col -> col.setAutoWidth(true));
        requiredProductsGrid.addThemeVariants(GridVariant.LUMO_ROW_STRIPES);
        requiredProductsGrid.setVisible(false);

        nameProductComboBox.setItems(productService.getProducts());
        nameProductComboBox.setItemLabelGenerator(Product::getName);
        quantityProductNumberField.setHasControls(true);

        returnButton.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
        returnButton.setVisible(false);

        // TODO probem ze skalowaniem
    }

    private void configureGrid() {
        horizontalLayoutRecipe.setWidthFull();
        deleteRecipeButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY, ButtonVariant.LUMO_ERROR);
        deleteRecipeButton.setVisible(false);

        horizontalLayoutProduct.setWidthFull();
        horizontalLayoutProduct.setVisible(false);
        deleteProductButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY, ButtonVariant.LUMO_ERROR);
        deleteProductButton.setVisible(false);
    }

    private void updateRecipeGrid() {
        recipeGrid.setItems(recipeService.getAllRecipes());
    }

    private void updateRequiredProductsGrid(Long recipeId) {
        requiredProductsGrid.setItems(requiredProductsService.getAllRequiredProductsByRecipeId(recipeId));
    }

    private Notification createAcceptNotificationForRecipe(Set<Recipe2> selectedItems) {
        Notification notification = new Notification();
        notification.setPosition(Notification.Position.TOP_CENTER);
        notification.addThemeVariants(NotificationVariant.LUMO_ERROR);

        Icon icon = VaadinIcon.WARNING.create();

        String text;
        if (selectedItems.size() == 1) {
            Recipe2 recipe = selectedItems.iterator().next();
            text = String.format("You want to delete %s recipe! Are you sure?", recipe.getName());
        } else {
            text = String.format("You want to delete %d recipes! Are you sure?", selectedItems.size());
        }

        Div info = new Div(new Text(text));

        Button yesButton = new Button("Yes",
                yesClickEvent -> {
                    recipeService.deleteSelectedRecipes(selectedItems);
                    notification.close();
                    updateRecipeGrid();
                    Notification.show("Deleted");
                });
        yesButton.getStyle().set("margin", "0 0 0 var(--lumo-space-l)");

        HorizontalLayout layout = new HorizontalLayout(icon, info, yesButton, createCloseBtn(notification));
        layout.setAlignItems(FlexComponent.Alignment.CENTER);

        notification.add(layout);

        return notification;
    }

    private Notification createAcceptNotificationForProduct(Set<RequiredProducts> selectedItems, Long recipeId) {
        Notification notification = new Notification();
        notification.setPosition(Notification.Position.TOP_CENTER);
        notification.addThemeVariants(NotificationVariant.LUMO_ERROR);

        Icon icon = VaadinIcon.WARNING.create();

        String text;
        if (selectedItems.size() == 1) {
            RequiredProducts requiredProduct = selectedItems.iterator().next();
            text = String.format("You want to remove %s from your recipe! Are you sure?", requiredProduct.getRequiredProductId());
            Optional<Product> pr = productService.getProduct(requiredProduct.getRequiredProductId());
        } else {
            text = String.format("You want to delete %d products from your recipe! Are you sure?", selectedItems.size());
        }

        Div info = new Div(new Text(text));

        Button yesButton = new Button("Yes",
                yesClickEvent -> {
                    requiredProductsService.deleteSelectedRequiredProducts(selectedItems);
                    notification.close();
                    updateRequiredProductsGrid(recipeId);
                    Notification.show("Deleted");
                });
        yesButton.getStyle().set("margin", "0 0 0 var(--lumo-space-l)");

        HorizontalLayout layout = new HorizontalLayout(icon, info, yesButton, createCloseBtn(notification));
        layout.setAlignItems(FlexComponent.Alignment.CENTER);

        notification.add(layout);

        return notification;
    }

    private Button createCloseBtn(Notification notification) {
        Button closeBtn = new Button(VaadinIcon.CLOSE_SMALL.create(),
                closeClickEvent -> notification.close());
        closeBtn.addThemeVariants(LUMO_TERTIARY_INLINE);

        return closeBtn;
    }

    private void createEditFields() {
        nameEditField.setWidthFull();
        binder.forField(nameEditField)
                .asRequired("Name must not be empty")
                .bind(Recipe2::getName, Recipe2::setName);
        recipeGrid.getColumnByKey("name").setEditorComponent(nameEditField);

        descriptionEditField.setWidthFull();
        binder.forField(descriptionEditField)
                .asRequired("Description must not be empty")
                .bind(Recipe2::getDescription, Recipe2::setDescription);
        recipeGrid.getColumnByKey("description").setEditorComponent(descriptionEditField);

        // TODO Jest rzucany błąd gdy klikniemy na dwa guziki tego rodzaju (editor jest pełny)
    }

}
