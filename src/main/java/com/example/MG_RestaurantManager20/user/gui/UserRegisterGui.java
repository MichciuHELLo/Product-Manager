package com.example.MG_RestaurantManager20.user.gui;

import com.example.MG_RestaurantManager20.user.domain.User;
import com.example.MG_RestaurantManager20.user.domain.UserRole;
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
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import java.util.Optional;

@Route("Register")
@PageTitle("Register")
public class UserRegisterGui extends Composite {

    private final UserService userService;

    public UserRegisterGui(UserService userService) {
        this.userService = userService;
    }

    @Override
    protected Component initContent() {

        TextField textFieldName = new TextField("Name");
        TextField textFieldSurname = new TextField("Surname");
        TextField textFieldPhoneNumber = new TextField("Phone Number");
        EmailField emailField = new EmailField("Email");
        PasswordField passwordField1 = new PasswordField("Password");
        PasswordField passwordField2 = new PasswordField("Repeat password");

        textFieldName.setRequired(true);
        textFieldSurname.setRequired(true);
        textFieldPhoneNumber.setRequired(true);
        textFieldPhoneNumber.setPattern("^[0-9\\s]+");
        textFieldPhoneNumber.setErrorMessage("Numbers only!");
        emailField.setErrorMessage("Please enter a valid email address");
        emailField.setRequiredIndicatorVisible(true);
        passwordField1.setRequired(true);
        passwordField2.setRequired(true);

        VerticalLayout verticalLayout = new VerticalLayout(
                new H2("Register"),
                textFieldName,
                textFieldSurname,
                textFieldPhoneNumber,
                emailField,
                passwordField1,
                passwordField2,
                new Button("Register", event -> register(
                        textFieldName.getValue(),
                        textFieldSurname.getValue(),
                        textFieldPhoneNumber.getValue(),
                        emailField.getValue(),
                        passwordField1.getValue(),
                        passwordField2.getValue()
                ))
        );
        verticalLayout.setAlignItems(FlexComponent.Alignment.CENTER);

        return verticalLayout;
    }

    private void register(String textFieldName, String textFieldSurname, String textFieldPhoneNumber, String emailField, String passwordField1, String passwordField2) {
        if (textFieldName.trim().isEmpty())
            Notification.show("Enter your name.").setPosition(Notification.Position.BOTTOM_CENTER);
        else if (textFieldSurname.trim().isEmpty())
            Notification.show("Enter your surname.").setPosition(Notification.Position.BOTTOM_CENTER);
        else if (textFieldPhoneNumber.trim().isEmpty())
            Notification.show("Enter your phone number.").setPosition(Notification.Position.BOTTOM_CENTER);
        else if (emailField.trim().isEmpty())
            Notification.show("Enter your e-mail.").setPosition(Notification.Position.BOTTOM_CENTER);
        else if (passwordField1.trim().isEmpty())
            Notification.show("Enter your password.").setPosition(Notification.Position.BOTTOM_CENTER);
        else if (passwordField2.trim().isEmpty())
            Notification.show("Confirm your password.").setPosition(Notification.Position.BOTTOM_CENTER);
        else if (!passwordField1.equals(passwordField2))
            Notification.show("Passwords don't match.").setPosition(Notification.Position.BOTTOM_CENTER);
        else{
            Optional<User> user = userService.getUserByEmail(emailField);
            if (user.isEmpty()){
                userService.addNewUser(new User(textFieldName, textFieldSurname, textFieldPhoneNumber, emailField, passwordField1, UserRole.ADMIN));
                Notification.show("Registration succeeded!").setPosition(Notification.Position.BOTTOM_CENTER);
                UI.getCurrent().navigate(UserSignInGui.class);
            }
            else
                Notification.show("Account with given e-mail already exists.").setPosition(Notification.Position.BOTTOM_CENTER);
        }
    }
}
