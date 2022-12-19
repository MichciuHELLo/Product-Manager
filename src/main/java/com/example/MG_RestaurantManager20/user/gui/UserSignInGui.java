package com.example.MG_RestaurantManager20.user.gui;

import com.example.MG_RestaurantManager20.auth.UserSession;
import com.example.MG_RestaurantManager20.employee.domain.Employee;
import com.example.MG_RestaurantManager20.employee.gui.EmployeeWorkGui;
import com.example.MG_RestaurantManager20.employee.service.EmployeeService;
import com.example.MG_RestaurantManager20.user.domain.User;
import com.example.MG_RestaurantManager20.user.domain.UserRole;
import com.example.MG_RestaurantManager20.user.service.UserService;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
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
    private final UserSession userSession;
    private final EmployeeService employeeService;

    public UserSignInGui(UserService userService, UserSession userSession, EmployeeService employeeService) {
        this.userService = userService;
        this.userSession = userSession;
        this.employeeService = employeeService;
    }

    @Override
    protected Component initContent() {

        EmailField emailField = new EmailField("Email");
        PasswordField passwordField = new PasswordField("Password");
        ComboBox<UserRole> roleComboBox = new ComboBox<>("Role");
        roleComboBox.setItems(UserRole.ADMIN, UserRole.EMPLOYEE);

        emailField.setErrorMessage("Please enter a valid email address");
        emailField.setRequiredIndicatorVisible(true);
        passwordField.setRequired(true);

        VerticalLayout verticalLayout = new VerticalLayout(
                new H2("Sing In"),
                emailField,
                passwordField,
                roleComboBox,
                new Button("Login", event -> signIn(
                        emailField.getValue(),
                        passwordField.getValue(),
                        roleComboBox.getValue()
                )),
                new RouterLink("Forgot password?", UserChangePasswordGui.class),
                new RouterLink("New account? Register", UserRegisterGui.class)
        );
        verticalLayout.setAlignItems(FlexComponent.Alignment.CENTER);

        return verticalLayout;
    }

    private void signIn(String emailField, String passwordField, UserRole userRole) {
        if (emailField.trim().isEmpty())
            Notification.show("Enter your e-mail.").setPosition(Notification.Position.BOTTOM_CENTER);
        else if (passwordField.trim().isEmpty())
            Notification.show("Enter your password.").setPosition(Notification.Position.BOTTOM_CENTER);
        else if (userRole == null){
            Notification.show("Enter your role.").setPosition(Notification.Position.BOTTOM_CENTER);
        }
        else {
            if (userRole.equals(UserRole.ADMIN)){
                Optional<User> user = userService.getUserByEmail(emailField);
                if (user.isEmpty()){
                    Notification.show("Wrong credentials.").setPosition(Notification.Position.BOTTOM_CENTER);
                }
                else {
                    if (user.get().checkPassword(passwordField)) {
                        userSession.createNewSession(user.get().getId(), UserRole.ADMIN);
                        UI.getCurrent().navigate(UserMainMenu.class);
                    }
                    else {
                        Notification.show("Wrong credentials.").setPosition(Notification.Position.BOTTOM_CENTER);
                    }
                }
            }
            else {
                Optional<Employee> employee = employeeService.getEmployeeByEmail(emailField);
                if (employee.isEmpty()){
                    Notification.show("Wrong credentials.").setPosition(Notification.Position.BOTTOM_CENTER);
                }
                else{
                    if (employee.get().getTempFile()) {
                        UI.getCurrent().navigate(UserChangePasswordGui.class);
                    }
                    else {
                        if (employee.get().checkPassword(passwordField)) {
                            userSession.createNewSession(employee.get().getId(), UserRole.EMPLOYEE);
                            UI.getCurrent().navigate(EmployeeWorkGui.class);
                        }
                        else {
                            Notification.show("Wrong credentials.").setPosition(Notification.Position.BOTTOM_CENTER);
                        }
                    }
                }
            }
        }
    }
}
