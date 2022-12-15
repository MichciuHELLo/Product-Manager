package com.example.MG_RestaurantManager20.user.gui;

import com.example.MG_RestaurantManager20.auth.UserSession;
import com.example.MG_RestaurantManager20.employee.domain.Employee;
import com.example.MG_RestaurantManager20.employee.gui.EmployeeMainMenu;
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
                // TODO forgot password link should be added
                new Button("Login", event -> signIn(
                        emailField.getValue(),
                        passwordField.getValue(),
                        roleComboBox.getValue()
                )),
                new RouterLink("New account? Register", UserRegisterGui.class)
        );
        verticalLayout.setAlignItems(FlexComponent.Alignment.CENTER);

        return verticalLayout;
    }

    private void signIn(String emailField, String passwordField, UserRole userRole) {
        if (emailField.trim().isEmpty())
            Notification.show("Enter your e-mail.");
        else if (passwordField.trim().isEmpty())
            Notification.show("Enter your password.");
        else if (userRole == null){
            Notification.show("Enter your role.");
        }
        else {
            if (userRole.equals(UserRole.ADMIN)){
                Optional<User> user = userService.getUserByEmail(emailField);
                if (user.isEmpty()){
                    Notification.show("Wrong credentials.");
                }
                else {
                    userSession.createNewSession(user.get().getId());
                    UI.getCurrent().navigate(UserMainMenu.class);
                }
            }
            else {
                Optional<Employee> employee = employeeService.getEmployeeByEmail(emailField);
                if (employee.isEmpty()){
                    Notification.show("Wrong credentials.");
                }
                else{
                    if (employee.get().getTempFile()) {
                        Notification.show("Not implemented yet.");
                    }
                    else {
                        userSession.createNewSession(employee.get().getId());
                        UI.getCurrent().navigate(EmployeeMainMenu.class);
                    }
                }
            }
        }
    }
}
