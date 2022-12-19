package com.example.MG_RestaurantManager20.user.gui;

import com.example.MG_RestaurantManager20.employee.domain.Employee;
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

import java.util.Optional;

@Route("Change_password")
@PageTitle("Change password")
public class UserChangePasswordGui extends Composite {

    private final UserService userService;
    private final EmployeeService employeeService;

    public UserChangePasswordGui(UserService userService, EmployeeService employeeService) {
        this.userService = userService;
        this.employeeService = employeeService;
    }

    @Override
    protected Component initContent() {
        EmailField emailField = new EmailField("Email");
        PasswordField currentPasswordField = new PasswordField("Current password");
        PasswordField newPasswordField = new PasswordField("New password");
        PasswordField newPasswordField2 = new PasswordField("Repeat new password");
        ComboBox<UserRole> roleComboBox = new ComboBox<>("Role");
        roleComboBox.setItems(UserRole.ADMIN, UserRole.EMPLOYEE);

        emailField.setErrorMessage("Please enter a valid email address");
        emailField.setRequiredIndicatorVisible(true);
        currentPasswordField.setRequired(true);
        newPasswordField.setRequired(true);
        newPasswordField2.setRequired(true);
        roleComboBox.setRequired(true);

        VerticalLayout verticalLayout = new VerticalLayout(
                new H2("Change your password"),
                emailField,
                // TODO forgot password ma pole currentPasswordField?
                currentPasswordField,
                newPasswordField,
                newPasswordField2,
                roleComboBox,
                new Button("Change password", event -> changePassword(
                        emailField.getValue(),
                        currentPasswordField.getValue(),
                        newPasswordField.getValue(),
                        newPasswordField2.getValue(),
                        roleComboBox.getValue()
                ))
        );
        verticalLayout.setAlignItems(FlexComponent.Alignment.CENTER);

        return verticalLayout;
    }

    private void changePassword(String emailField, String currentPasswordField, String newPasswordField, String newPasswordField2, UserRole userRole) {
        if (emailField.trim().isEmpty())
            Notification.show("Enter your e-mail.").setPosition(Notification.Position.BOTTOM_CENTER);
        else if (currentPasswordField.trim().isEmpty())
            Notification.show("Enter your password.").setPosition(Notification.Position.BOTTOM_CENTER);
        else if (newPasswordField.trim().isEmpty())
            Notification.show("Enter your new password.").setPosition(Notification.Position.BOTTOM_CENTER);
        else if (newPasswordField2.trim().isEmpty())
            Notification.show("Repeat new password.").setPosition(Notification.Position.BOTTOM_CENTER);
        else if (userRole == null)
            Notification.show("Enter your role.").setPosition(Notification.Position.BOTTOM_CENTER);
        else if (!newPasswordField.equals(newPasswordField2))
            Notification.show("New passwords aren't the same.").setPosition(Notification.Position.BOTTOM_CENTER);
        else {
            if (userRole.equals(UserRole.ADMIN)){
                Optional<User> user = userService.getUserByEmail(emailField);
                if (user.isEmpty()){
                    Notification.show("Wrong credentials.").setPosition(Notification.Position.BOTTOM_CENTER);
                }
                else {
                    if (user.get().checkPassword(currentPasswordField)) {
                        userService.changePassword(emailField, newPasswordField);
                        UI.getCurrent().navigate(UserSignInGui.class);
                        Notification.show("Password changed.").setPosition(Notification.Position.BOTTOM_CENTER);
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
                    if (employee.get().checkPassword(currentPasswordField)) {
                        employeeService.changePassword(emailField, newPasswordField);
                        UI.getCurrent().navigate(UserSignInGui.class);
                        Notification.show("Password changed.").setPosition(Notification.Position.BOTTOM_CENTER);
                    }
                    else {
                        Notification.show("Wrong credentials.").setPosition(Notification.Position.BOTTOM_CENTER);
                    }
                }
            }
        }
    }

}
