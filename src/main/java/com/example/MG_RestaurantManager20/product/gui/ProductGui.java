package com.example.MG_RestaurantManager20.product.gui;

import com.example.MG_RestaurantManager20.auth.UserSession;
import com.example.MG_RestaurantManager20.product.domain.Product;
import com.example.MG_RestaurantManager20.product.domain.ProductUnit;
import com.example.MG_RestaurantManager20.product.service.ProductService;
import com.example.MG_RestaurantManager20.user.gui.UserSignInGui;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.UI;
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
import lombok.extern.slf4j.Slf4j;

import java.util.Set;

import static com.vaadin.flow.component.button.ButtonVariant.LUMO_TERTIARY_INLINE;

@Slf4j
@Route("Products")
@PageTitle("Products")
public class ProductGui extends VerticalLayout {

    private final UserSession userSession;

    private final ProductService productService;
    private final Binder<Product> binder = new Binder<>(Product.class);

    private final Grid<Product> productGrid = new Grid<>(Product.class);
    private final TextField nameTextField = new TextField("Name");
    private final NumberField minNumberField = new NumberField("Minimum");
    private final NumberField quantityNumberField = new NumberField("Quantity");
    private final ComboBox<ProductUnit> unitComboBoxField = new ComboBox<>("Unit");
    private final Button addProductButton = new Button("Add", new Icon(VaadinIcon.PLUS));
    private final Button deleteProductButton = new Button("Delete", new Icon(VaadinIcon.TRASH));

    private final TextField nameEditField = new TextField();
    private final NumberField minEditField = new NumberField();
    private final NumberField quantityEditField = new NumberField();
    private final ComboBox<ProductUnit> unitEditField = new ComboBox<>();

    private final HorizontalLayout horizontalLayout =
            new HorizontalLayout(nameTextField, minNumberField, quantityNumberField, unitComboBoxField, addProductButton, deleteProductButton);

    public ProductGui(UserSession userSession, ProductService productService) {
        this.userSession = userSession;
        this.productService = productService;

        if (userSession.checkIfAuthenticated()) {
            setSizeFull();
            configureGrid();
            configureView();

            add(productGrid, horizontalLayout);
            updateGrid();

            productGrid.addSelectionListener(selection -> deleteProductButton.setVisible(productGrid.getSelectedItems().size() > 0));

            addProductButton.addClickListener(buttonClickEvent -> {
                if (nameTextField.getValue().isEmpty() || minNumberField.getValue() == null || quantityNumberField.getValue() == null || unitComboBoxField.getValue() == null) {
                    Notification.show("Fill all the fields.").setPosition(Notification.Position.BOTTOM_CENTER);
                } else {
                    if (productService.getProductByNameAndUserSessionId(nameTextField.getValue(), userSession.getUserSessionId()).isPresent()) {
                        Notification.show(String.format("Product with this name %s already exists!", nameTextField.getValue())).setPosition(Notification.Position.BOTTOM_CENTER);
                    } else {
                        String name = nameTextField.getValue();
                        name = name.substring(0, 1).toUpperCase() + name.substring(1).toLowerCase();

                        productService.addNewProduct(
                                new Product(userSession.getUserSessionId(), name, minNumberField.getValue(), quantityNumberField.getValue(), unitComboBoxField.getValue()));

                        log.info(String.format("User %s added product %s to Products", userSession.getUserSessionId(), name));

                        nameTextField.clear();
                        minNumberField.clear();
                        quantityNumberField.clear();
                        unitComboBoxField.clear();

                        updateGrid();
                    }
                }
            });

// EDIT
            Editor<Product> editor = productGrid.getEditor();
            Grid.Column<Product> editColumn = productGrid.addComponentColumn(product -> {
                Button editButton = new Button("Edit");
                editButton.addClickListener(e -> {
                    editor.editItem(product);
                });
                editButton.setEnabled(!editor.isOpen());
                return editButton;
            });
            editor.setBinder(binder);
            editor.setBuffered(true);

            createEditFields();

            Button saveButton = new Button("Save", e -> {
                if (!(editor.getItem().getName().equals(nameEditField.getValue()) && editor.getItem().getMin().equals(minEditField.getValue()) && editor.getItem().getQuantity().equals(quantityEditField.getValue()) && editor.getItem().getProductUnit().equals(unitEditField.getValue())
                        || nameEditField.getValue().isEmpty() || minEditField.getValue() == null || quantityEditField.getValue() == null || unitEditField.isEmpty())) {
                    if (productService.getProductByNameAndUserSessionId(nameTextField.getValue(), userSession.getUserSessionId()).isEmpty()) {
                        productService.updateProduct(editor.getItem().getId(), new Product(nameEditField.getValue(), minEditField.getValue(), quantityEditField.getValue(), unitEditField.getValue()));
                        editor.save();
                        log.info(String.format("User %s added product %s to Products", userSession.getUserSessionId(), nameEditField.getValue()));
                    } else {
                        Notification.show(String.format("Product with this name %s already exists!", nameEditField.getValue())).setPosition(Notification.Position.BOTTOM_CENTER);
                    }
                }
            });
            Button cancelButton = new Button(VaadinIcon.CLOSE.create(), e -> editor.cancel());
            cancelButton.addThemeVariants(ButtonVariant.LUMO_ICON, ButtonVariant.LUMO_ERROR);
            HorizontalLayout actions = new HorizontalLayout(saveButton, cancelButton);
            actions.setPadding(false);
            editColumn.setEditorComponent(actions);
// EDIT

            deleteProductButton.addClickListener(buttonClickEvent -> {
                if (productGrid.getSelectedItems().size() > 0) {
                    Notification notification = createAcceptNotification(productGrid.getSelectedItems());
                    notification.open();
                }
            });
        } else {
            UI.getCurrent().navigate(UserSignInGui.class);
            UI.getCurrent().getPage().reload();
        }


    }

    private void configureGrid() {
        productGrid.setSelectionMode(Grid.SelectionMode.MULTI);
        productGrid.setSizeFull();
        productGrid.setColumns("name", "min", "quantity", "productUnit");
        productGrid.getColumns().forEach(col -> col.setAutoWidth(true));
        productGrid.addThemeVariants(GridVariant.LUMO_ROW_STRIPES);
    }

    private void configureView() {
        horizontalLayout.setWidthFull();

        minNumberField.setMin(0D);
        minNumberField.setHasControls(true);
        quantityEditField.setMin(0D);
        quantityEditField.setHasControls(true);

        unitComboBoxField.setItems(ProductUnit.KILOS, ProductUnit.GRAMS, ProductUnit.LITERS, ProductUnit.MILLILITERS, ProductUnit.UNITS);
        unitEditField.setItems(ProductUnit.KILOS, ProductUnit.GRAMS, ProductUnit.LITERS, ProductUnit.MILLILITERS, ProductUnit.UNITS);

        minEditField.setMin(0D);
        minEditField.setHasControls(true);
        quantityNumberField.setMin(0D);
        quantityNumberField.setHasControls(true);

        deleteProductButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY, ButtonVariant.LUMO_ERROR);
        deleteProductButton.setVisible(false);
    }

    private void updateGrid() {
        productGrid.setItems(productService.getProductsByUserSessionId(userSession.getUserSessionId()));
    }

    private Notification createAcceptNotification(Set<Product> selectedItems) {
        Notification notification = new Notification();
        notification.setPosition(Notification.Position.TOP_CENTER);
        notification.addThemeVariants(NotificationVariant.LUMO_ERROR);

        Icon icon = VaadinIcon.WARNING.create();

        String text;
        if (selectedItems.size() == 1) {
            Product product = selectedItems.iterator().next();
            text = String.format("You want to delete %s product! Are you sure?", product.getName());
        } else {
            text = String.format("You want to delete %d products! Are you sure?", selectedItems.size());
        }

        Div info = new Div(new Text(text));

        Button yesButton = new Button("Yes",
                clickEvent -> {
                    log.info(String.format("User %s deleted %d products", userSession.getUserSessionId(), selectedItems.size()));
                    productService.deleteProducts(selectedItems);
                    notification.close();
                    updateGrid();
                    Notification.show("Deleted").setPosition(Notification.Position.BOTTOM_CENTER);
                });
        yesButton.getStyle().set("margin", "0 0 0 var(--lumo-space-l)");

        HorizontalLayout layout = new HorizontalLayout(icon, info, yesButton, createCloseBtn(notification));
        layout.setAlignItems(FlexComponent.Alignment.CENTER);

        notification.add(layout);

        return notification;
    }

    private Button createCloseBtn(Notification notification) {
        Button closeBtn = new Button(VaadinIcon.CLOSE_SMALL.create(),
                clickEvent -> notification.close());
        closeBtn.addThemeVariants(LUMO_TERTIARY_INLINE);

        return closeBtn;
    }

    private void createEditFields() {
        nameEditField.setWidthFull();
        binder.forField(nameEditField)
                .asRequired("Product name must not be empty")
                .bind(Product::getName, Product::setName);
        productGrid.getColumnByKey("name").setEditorComponent(nameEditField);

        minEditField.setWidthFull();
        binder.forField(minEditField)
                .asRequired("Minimum must not be empty")
                .bind(Product::getMin, Product::setMin);
        productGrid.getColumnByKey("min").setEditorComponent(minEditField);

        quantityEditField.setWidthFull();
        binder.forField(quantityEditField)
                .asRequired("Quantity must not be empty")
                .bind(Product::getQuantity, Product::setQuantity);
        productGrid.getColumnByKey("quantity").setEditorComponent(quantityEditField);

        unitEditField.setWidthFull();
        binder.forField(unitEditField)
                .asRequired("Product unit must not be empty")
                .bind(Product::getProductUnit, Product::setProductUnit);
        productGrid.getColumnByKey("productUnit").setEditorComponent(unitEditField);
    }
}
