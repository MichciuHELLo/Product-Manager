package com.example.MG_RestaurantManager20.user.gui;

import com.example.MG_RestaurantManager20.user.domain.User;
import com.example.MG_RestaurantManager20.user.service.UserService;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouterLink;

import java.util.Optional;

@Route("Sign_In")
@PageTitle("Sign In")
public class UserSignInGui extends Composite {

    private final UserService userService;

    public UserSignInGui(UserService userService) {
        this.userService = userService;
    }

    @Override
    protected Component initContent() {

        EmailField emailField = new EmailField("Email");
        PasswordField passwordField = new PasswordField("Password");

        emailField.setErrorMessage("Please enter a valid email address");
        emailField.setRequiredIndicatorVisible(true);
        passwordField.setRequired(true);

        VerticalLayout verticalLayout = new VerticalLayout(
                new H2("Sing In"),
                emailField,
                passwordField,
                // TODO forgot password link should be added
                new Button("Login", event -> signIn(
                        emailField.getValue(),
                        passwordField.getValue()
                )),
                new RouterLink("New account? Register", UserRegisterGui.class)
        );
        verticalLayout.setAlignItems(FlexComponent.Alignment.CENTER);

        return verticalLayout;
    }

    private void signIn(String emailField, String passwordField) {
        if (emailField.trim().isEmpty())
            Notification.show("Enter your e-mail.");
        else if (passwordField.trim().isEmpty())
            Notification.show("Enter your password.");
        else {
            Optional<User> user = userService.getUserByEmail(emailField);
            if (user.isEmpty()){
                Notification.show("Wrong credentials.");
            }
            else
                UI.getCurrent().navigate(UserMainMenu.class);
        }
    }
}
