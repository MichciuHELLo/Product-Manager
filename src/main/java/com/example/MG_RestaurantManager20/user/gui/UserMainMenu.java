package com.example.MG_RestaurantManager20.user.gui;

import com.example.MG_RestaurantManager20.auth.UserSession;
import com.example.MG_RestaurantManager20.employee.gui.EmployeeGui;
import com.example.MG_RestaurantManager20.product.gui.ProductGui;
import com.example.MG_RestaurantManager20.recipe2.gui.RecipeGui2;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.StreamResource;

@Route("Main_Menu")
@PageTitle("Restaurant Manager - Menu")
public class UserMainMenu extends Composite {

    private final UserSession userSession;

    private final StreamResource imageResource = new StreamResource("RMMG_logo.png", () -> getClass().getResourceAsStream("/images/RMMG_logo.png"));
    private final Image logoImage = new Image(imageResource, "My RMMG logo");
    private final HorizontalLayout header = new HorizontalLayout(logoImage);

    Button logOutButton = new Button("Log out", new Icon(VaadinIcon.SIGN_OUT));

    public UserMainMenu(UserSession userSession) {
        this.userSession = userSession;
    }

    @Override
    protected Component initContent() {
        if (userSession.checkIfAuthenticated()) {

            configureView();

            VerticalLayout verticalLayout = new VerticalLayout(
                    new H1("Main menu"),
                    new Button("Recipes", new Icon(VaadinIcon.LIST), event -> UI.getCurrent().navigate(RecipeGui2.class)),
                    new Button("Products", new Icon(VaadinIcon.PACKAGE), event -> UI.getCurrent().navigate(ProductGui.class)),
                    new Button("Employees", new Icon(VaadinIcon.GROUP), event -> UI.getCurrent().navigate(EmployeeGui.class)),
                    logOutButton
            );
            verticalLayout.setAlignItems(FlexComponent.Alignment.CENTER);

            logOutButton.addClickListener(event -> {
                userSession.removeCurrentSession();
                UI.getCurrent().navigate(UserSignInGui.class);
            });

            return new VerticalLayout(header, verticalLayout);
        } else {
            UI.getCurrent().navigate(UserSignInGui.class);
            UI.getCurrent().getPage().reload();
            return new VerticalLayout();
        }

    }

    private void configureView() {
        header.setWidthFull();
        logOutButton.addThemeVariants(ButtonVariant.LUMO_ERROR);
    }

}
