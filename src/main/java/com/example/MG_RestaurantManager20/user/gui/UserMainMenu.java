package com.example.MG_RestaurantManager20.user.gui;

import com.example.MG_RestaurantManager20.product.gui.ProductGui;
import com.example.MG_RestaurantManager20.recipe.gui.RecipeGui;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@Route("Main_Menu")
@PageTitle("Restaurant Manager - Menu")
public class UserMainMenu extends Composite {

    @Override
    protected Component initContent() {

        VerticalLayout verticalLayout = new VerticalLayout(
                new H1("Main menu"),
                new Button("Work", new Icon(VaadinIcon.WORKPLACE),event -> Notification.show("Not implemented yet!")),
                new Button("Products", new Icon(VaadinIcon.PACKAGE), event -> UI.getCurrent().navigate(ProductGui.class)),
                new Button("Recipes", new Icon(VaadinIcon.LIST), event -> UI.getCurrent().navigate(RecipeGui.class)),
                new Button("Employees", new Icon(VaadinIcon.GROUP), event -> Notification.show("Not implemented yet!")),
                new Button("Statistics", new Icon(VaadinIcon.CHART), event -> Notification.show("Not implemented yet!")),
                new Button("Options", new Icon(VaadinIcon.COG), event -> Notification.show("Not implemented yet!"))
        );
        verticalLayout.setAlignItems(FlexComponent.Alignment.CENTER);

        return verticalLayout;
    }

}
