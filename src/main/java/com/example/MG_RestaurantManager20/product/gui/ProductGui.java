package com.example.MG_RestaurantManager20.product.gui;

import com.example.MG_RestaurantManager20.product.adapters.database.ProductRepository;
import com.example.MG_RestaurantManager20.product.adapters.web.ProductController;
import com.example.MG_RestaurantManager20.product.domain.Product;
import com.example.MG_RestaurantManager20.product.domain.ProductUnit;
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

@Route("Products")
public class ProductGui extends VerticalLayout {

    // ------ Adding visible part ------ //

    private final Grid<Product> gridProducts;

    // Adding fields ---!
    // private final Span spanAdd;
    private final TextField textFieldAddName;
    private final NumberField numberFieldAddMin;
    private final NumberField numberFieldAddQuantity;
    private final ComboBox<ProductUnit> comboBoxAddUnit;
    // private Button buttonAddProduct;

    // Editing fields ---!
    // private final Span spanEdit;
    private final IntegerField integerFieldEditID;
    private final TextField textFieldEditName;
    private final NumberField numberFieldEditMin;
    private final NumberField numberFieldEditQuantity;
    private final ComboBox<ProductUnit> comboBoxEditUnit;
    // private Button buttonEditProduct;

    // Deleting fields ---!
    // private final Span spanDelete;
    private final IntegerField integerFieldDeleteID;
    private final RadioButtonGroup<String> radioButtonGroupDelete;
    private final Checkbox checkboxConfirmationDelete;
    // private Button buttonDeleteProduct;

    private Notification notification;

    @Autowired
    public ProductGui(ProductRepository productRepository, ProductController productController) {

        // ------ Setting up the visible part ------ //
        gridProducts = new Grid<>(Product.class);
        gridProducts.setItems(productController.getProducts());
        gridProducts.setColumns("id", "name", "min", "quantity", "productUnit");


        // ------------------------ Adding product fields ------------------------ //

        Span spanAdd = new Span();
        textFieldAddName = new TextField("Name:");
        numberFieldAddMin = new NumberField("Minimum");
        numberFieldAddQuantity = new NumberField("Quantity");
        comboBoxAddUnit = new ComboBox<>("Unit", ProductUnit.values());
        Button buttonAddProduct = new Button("Add new product", new Icon(VaadinIcon.PLUS));

        // ------ Setting up adding fields ( visual ) ------ //
        buttonAddProduct.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        numberFieldAddMin.setWidthFull();
        numberFieldAddMin.setHasControls(true);
        numberFieldAddQuantity.setWidthFull();
        numberFieldAddQuantity.setHasControls(true);
        spanAdd.getElement().setProperty("innerHTML", "<h1>Adding zone</h1>");

        var addingLayout = new VerticalLayout(spanAdd, textFieldAddName, numberFieldAddMin, numberFieldAddQuantity, comboBoxAddUnit, buttonAddProduct);
        addingLayout.setSizeFull();
        addingLayout.setSpacing(true);
        addingLayout.setAlignItems(Alignment.STRETCH);

        // ------ Action of the adding button ------ //
        buttonAddProduct.addClickListener(buttonClickEvent -> {
            if (textFieldAddName.isEmpty() || numberFieldAddMin.isEmpty() || numberFieldAddQuantity.isEmpty() || comboBoxAddUnit.isEmpty()) {
                notification = new Notification("Fill all fields!", 3000);
                notification.open();
            } else {
                String convertedName = textFieldAddName.getValue().toLowerCase();
                convertedName = convertedName.substring(0, 1).toUpperCase() + convertedName.toLowerCase().substring(1);

                Optional<Product> productOptional = productRepository.findProductByName(convertedName);
                if (productOptional.isPresent()) {
                    notification = new Notification("Product \"" + convertedName + "\" already exists!", 3000);
                    notification.open();
                } else {
                    if (numberFieldAddQuantity.getValue() < 0 || numberFieldAddMin.getValue() < 0) {
                        notification = new Notification("Minimum and Quantity can't be less then 0!", 3000);
                        notification.open();
                    } else {
                        Product product = new Product(convertedName, numberFieldAddMin.getValue(), numberFieldAddQuantity.getValue(), comboBoxAddUnit.getValue());
                        productController.addNewProduct(product);

                        gridProducts.setItems(productController.getProducts());

                        notification = new Notification("Product \"" + convertedName + "\" added!", 3000);
                        notification.open();

                        textFieldAddName.clear();
                        numberFieldAddMin.clear();
                        numberFieldAddQuantity.clear();
                        comboBoxAddUnit.clear();
                    }
                }
            }
        });


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
        spanDelete.getElement().setProperty("innerHTML", "<h1>Deleting zone</h1>");

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
                        boolean exists = productRepository.existsById(integerFieldDeleteID.getValue().longValue());
                        if (!exists) {
                            notification = new Notification("Product with this ID: '" + integerFieldDeleteID.getValue() + "' doesn't exists!", 3000);
                            notification.open();
                        } else {
                            productController.deleteProduct(integerFieldDeleteID.getValue().longValue());

                            gridProducts.setItems(productController.getProducts());

                            notification = new Notification("Product with this ID: '" + integerFieldDeleteID.getValue() + "' has been deleted!", 3000);
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
                        productController.deleteAllProducts();

                        gridProducts.setItems(productController.getProducts());

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

        var allLayouts = new HorizontalLayout(addingLayout, editingLayout, deletingLayout);
        allLayouts.setWidthFull();
        allLayouts.setSpacing(true);
        allLayouts.setAlignItems(Alignment.BASELINE);

        add(gridProducts);                      // Grid
        add(allLayouts);                        // All Layouts

    }
}
