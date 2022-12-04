package com.example.MG_RestaurantManager20.employee.gui;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@Route("Employee_Main_Menu")
@PageTitle("Restaurant Manager - Employee menu")
public class EmployeeMainMenu extends Composite {

    @Override
    protected Component initContent() {

        VerticalLayout verticalLayout = new VerticalLayout(
                new H1("Employee main menu"),
                new Button("Work", new Icon(VaadinIcon.WORKPLACE), event -> Notification.show("Not implemented yet!")),
                new Button("Statistics", new Icon(VaadinIcon.CHART), event -> Notification.show("Not implemented yet!"))
        );
        verticalLayout.setAlignItems(FlexComponent.Alignment.CENTER);

        return verticalLayout;
    }

}
