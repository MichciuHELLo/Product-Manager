package com.example.MG_RestaurantManager20.employee.gui;

import com.example.MG_RestaurantManager20.auth.UserSession;
import com.example.MG_RestaurantManager20.employee.domain.Employee;
import com.example.MG_RestaurantManager20.employee.service.EmployeeService;
import com.example.MG_RestaurantManager20.mail.service.EmailService;
import com.example.MG_RestaurantManager20.product.gui.ProductGui;
import com.example.MG_RestaurantManager20.recipe.gui.RecipeGui;
import com.example.MG_RestaurantManager20.user.gui.UserMainMenu;
import com.example.MG_RestaurantManager20.user.gui.UserSignInGui;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.grid.editor.Editor;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.StreamResource;

import java.time.LocalDate;
import java.util.Set;
import java.util.UUID;

import static com.vaadin.flow.component.button.ButtonVariant.LUMO_TERTIARY_INLINE;

@Route("Employees")
@PageTitle("Employees")
public class EmployeeGui extends VerticalLayout {

    private final UserSession userSession;

    private final StreamResource imageResource = new StreamResource("RMMG_logo.png", () -> getClass().getResourceAsStream("/images/RMMG_logo.png"));
    private final Image logoImage = new Image(imageResource, "My RMMG logo");
    private final HorizontalLayout headerLogo = new HorizontalLayout(logoImage);

    private final Button recipesButton = new Button("Recipes", new Icon(VaadinIcon.LIST));
    private final Button productsButton = new Button("Products", new Icon(VaadinIcon.PACKAGE));
    private final Button employeesButton = new Button("Employees", new Icon(VaadinIcon.GROUP));
    private final HorizontalLayout headerButtons = new HorizontalLayout(recipesButton, productsButton, employeesButton);

    private final Button logOutButton = new Button("Log out", new Icon(VaadinIcon.SIGN_OUT));
    private final HorizontalLayout headerLogOut = new HorizontalLayout(logOutButton);

    private final HorizontalLayout header = new HorizontalLayout(headerLogo, headerButtons, headerLogOut);

    private final EmployeeService employeeService;
    private final EmailService emailService;

    private final String EMAIL_MESSAGE = "Hi!\n\nRestaurant manager here. Your boss added you as a employee.\n\nHere's your temporary password: %s\nUse it to login to your new account!";

    private final Grid<Employee> employeeGrid = new Grid<>(Employee.class);
    private final TextField nameTextField = new TextField("Name");
    private final TextField surnameTextField = new TextField("Surname");
    private final EmailField emailField = new EmailField("Email");
    private final Button addEmployeeButton = new Button("Add", new Icon(VaadinIcon.PLUS));
    private final Button deleteEmployeeButton = new Button("Delete", new Icon(VaadinIcon.TRASH));

    private final Binder<Employee> binder = new Binder<>(Employee.class);

    private final TextField firstNameEditField = new TextField();
    private final TextField surnameEditField = new TextField();
    private final EmailField emailEditField = new EmailField();

    private final HorizontalLayout horizontalLayout =
            new HorizontalLayout(nameTextField, surnameTextField, emailField, addEmployeeButton, deleteEmployeeButton);

    public EmployeeGui(UserSession userSession, EmployeeService employeeService, EmailService emailService) {
        this.userSession = userSession;
        this.employeeService = employeeService;
        this.emailService = emailService;

        if (userSession.checkIfAuthenticatedAdmin()) {
            setSizeFull();
            configureGrid();
            configureView();

            H3 h3 = new H3("Your employees");
            add(header, h3, employeeGrid, horizontalLayout);
            updateGrid();

            logoImage.addClickListener(logoImageClickEvent -> UI.getCurrent().navigate(UserMainMenu.class));
            recipesButton.addClickListener(logoImageClickEvent -> UI.getCurrent().navigate(RecipeGui.class));
            productsButton.addClickListener(logoImageClickEvent -> UI.getCurrent().navigate(ProductGui.class));
            employeesButton.addClickListener(logoImageClickEvent -> UI.getCurrent().navigate(EmployeeGui.class));
            logOutButton.addClickListener(logoImageClickEvent -> {
                userSession.removeCurrentSession();
                UI.getCurrent().navigate(UserSignInGui.class);
            });

            employeeGrid.addSelectionListener(selection -> deleteEmployeeButton.setVisible(employeeGrid.getSelectedItems().size() > 0));

            addEmployeeButton.addClickListener(buttonClickEvent -> {
                if (nameTextField.getValue().isEmpty() || surnameTextField.getValue().isEmpty() || emailField.getValue().isEmpty()) {
                    Notification.show("Fill all the fields.").setPosition(Notification.Position.BOTTOM_CENTER);
                } else {
                    if (!emailField.isInvalid()) {

                        String name = nameTextField.getValue();
                        name = name.substring(0,1).toUpperCase() + name.substring(1).toLowerCase();

                        String surname = surnameTextField.getValue();
                        surname = surname.substring(0,1).toUpperCase() + surname.substring(1).toLowerCase();

                        String tempPassword = UUID.randomUUID().toString().replaceAll("-", "");
                        employeeService.addNewEmployee(
                                new Employee(userSession.getUserSessionId(), name, surname, emailField.getValue().toLowerCase(), LocalDate.now(), tempPassword, true));

                        emailService.sendEmail(emailField.getValue(), "Reset your password.", String.format(EMAIL_MESSAGE, tempPassword));

                        nameTextField.clear();
                        surnameTextField.clear();
                        emailField.clear();

                        updateGrid();
                    }
                }
            });

//EDIT
            Editor<Employee> editor = employeeGrid.getEditor();
            Grid.Column<Employee> editColumn = employeeGrid.addComponentColumn(employee -> {
                Button editButton = new Button("Edit");
                editButton.addClickListener(e -> {
                    editor.editItem(employee);
                });
                editButton.setEnabled(!editor.isOpen());
                return editButton;
            });
            editor.setBinder(binder);
            editor.setBuffered(true);

            createEditFields();

            Button saveButton = new Button("Save", e -> {
                if (!(editor.getItem().getFirstName().equals(firstNameEditField.getValue()) && editor.getItem().getSurname().equals(surnameEditField.getValue()) && editor.getItem().getEmail().equals(emailEditField.getValue())
                        || firstNameEditField.getValue().isEmpty() || surnameEditField.getValue().isEmpty() || emailEditField.getValue().isEmpty())) {
                    if (!editor.getItem().getEmail().equals(emailEditField.getValue())) {
                        if (employeeService.getEmployeeByEmail(emailEditField.getValue()).isPresent()) {
                            Notification.show(String.format("Employee with this email: %s already exists", emailEditField.getValue())).setPosition(Notification.Position.BOTTOM_CENTER);
                        } else {
                            employeeService.updateEmployee(editor.getItem().getId(), new Employee(firstNameEditField.getValue(), surnameEditField.getValue(), emailEditField.getValue(), editor.getItem().getEmployeeSince()));
                            editor.save();
                        }
                    } else {
                        employeeService.updateEmployee(editor.getItem().getId(), new Employee(firstNameEditField.getValue(), surnameEditField.getValue(), emailEditField.getValue(), editor.getItem().getEmployeeSince()));
                        editor.save();
                    }
                }
            });
            Button cancelButton = new Button(VaadinIcon.CLOSE.create(), e -> editor.cancel());
            cancelButton.addThemeVariants(ButtonVariant.LUMO_ICON, ButtonVariant.LUMO_ERROR);
            HorizontalLayout actions = new HorizontalLayout(saveButton, cancelButton);
            actions.setPadding(false);
            editColumn.setEditorComponent(actions);
//EDIT

            deleteEmployeeButton.addClickListener(buttonClickEvent -> {
                if (employeeGrid.getSelectedItems().size() > 0) {
                    Notification notification = createAcceptNotification(employeeGrid.getSelectedItems());
                    notification.open();
                }
            });
        } else {
            userSession.removeCurrentSession();
            UI.getCurrent().navigate(UserSignInGui.class);
            UI.getCurrent().getPage().reload();
        }


    }

    private void configureGrid() {
        employeeGrid.setSelectionMode(Grid.SelectionMode.MULTI);
        employeeGrid.setSizeFull();
        employeeGrid.setColumns("firstName", "surname", "email", "employeeSince");
        employeeGrid.getColumns().forEach(col -> col.setAutoWidth(true));
        employeeGrid.addThemeVariants(GridVariant.LUMO_ROW_STRIPES);
    }

    private void configureView() {
        header.setWidthFull();
        headerButtons.setWidthFull();
        headerButtons.setJustifyContentMode(JustifyContentMode.START);
        headerButtons.setSpacing(true);

        employeesButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        logOutButton.addThemeVariants(ButtonVariant.LUMO_ERROR);

        horizontalLayout.setWidthFull();
        emailField.setErrorMessage("Please enter a valid email address");
        emailField.setRequiredIndicatorVisible(true);
        deleteEmployeeButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY, ButtonVariant.LUMO_ERROR);
        deleteEmployeeButton.setVisible(false);
    }

    private void updateGrid() {
        employeeGrid.setItems(employeeService.getEmployeesByUserSessionId(userSession.getUserSessionId()));
    }

    private Notification createAcceptNotification(Set<Employee> selectedItems) {
        Notification notification = new Notification();
        notification.setPosition(Notification.Position.TOP_CENTER);
        notification.addThemeVariants(NotificationVariant.LUMO_ERROR);

        Icon icon = VaadinIcon.WARNING.create();

        String text;
        if (selectedItems.size() == 1) {
            Employee employee = selectedItems.iterator().next();
            text = String.format("You want to delete %s %s employee! Are you sure?", employee.getFirstName(), employee.getSurname());
        } else {
            text = String.format("You want to delete %d employees! Are you sure?", selectedItems.size());
        }

        Div info = new Div(new Text(text));

        Button yesButton = new Button("Yes",
                clickEvent -> {
                    employeeService.deleteEmployees(selectedItems);
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
        firstNameEditField.setWidthFull();
        binder.forField(firstNameEditField)
                .asRequired("First name must not be empty")
                .bind(Employee::getFirstName, Employee::setFirstName);
        employeeGrid.getColumnByKey("firstName").setEditorComponent(firstNameEditField);

        surnameEditField.setWidthFull();
        binder.forField(surnameEditField)
                .asRequired("Surname must not be empty")
                .bind(Employee::getSurname, Employee::setSurname);
        employeeGrid.getColumnByKey("surname").setEditorComponent(surnameEditField);

        emailEditField.setErrorMessage("Please enter a valid email address");
        emailEditField.setWidthFull();
        binder.forField(emailEditField)
                .asRequired("Email must not be empty")
                .bind(Employee::getEmail, Employee::setEmail);
        employeeGrid.getColumnByKey("email").setEditorComponent(emailEditField);
    }

}
