package com.example.MG_RestaurantManager20.user.gui;

import com.example.MG_RestaurantManager20.auth.UserSession;
import com.example.MG_RestaurantManager20.employee.gui.EmployeeGui;
import com.example.MG_RestaurantManager20.product.gui.ProductGui;
import com.example.MG_RestaurantManager20.recipe2.gui.RecipeGui2;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@Route("Main_Menu")
@PageTitle("Restaurant Manager - Menu")
public class UserMainMenu extends Composite {

    private final UserSession userSession;

    public UserMainMenu(UserSession userSession) {
        this.userSession = userSession;
    }

    @Override
    protected Component initContent() {
        if (userSession.checkIfAuthenticated()) {
            VerticalLayout verticalLayout = new VerticalLayout(
                    new H1("Main menu"),
                    new Button("Products", new Icon(VaadinIcon.PACKAGE), event -> UI.getCurrent().navigate(ProductGui.class)),
                    new Button("Recipes", new Icon(VaadinIcon.LIST), event -> UI.getCurrent().navigate(RecipeGui2.class)),
                    new Button("Employees", new Icon(VaadinIcon.GROUP), event -> UI.getCurrent().navigate(EmployeeGui.class))
            );
            verticalLayout.setAlignItems(FlexComponent.Alignment.CENTER);

            return verticalLayout;
        } else {
            UI.getCurrent().navigate(UserSignInGui.class);
            UI.getCurrent().getPage().reload();
            return new VerticalLayout();
        }

    }

}
