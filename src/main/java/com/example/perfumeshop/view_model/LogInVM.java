package com.example.perfumeshop.view_model;

import com.example.perfumeshop.model.Person;
import com.example.perfumeshop.model.Role;
import com.example.perfumeshop.view_model.commands.GetShopCommand;
import com.example.perfumeshop.view_model.commands.LoginCommand;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.util.Callback;

public class LogInVM {
    @FXML
    private TextField usernameTextField;
    @FXML
    private TextField passwordTextField;
    @FXML
    private Button signInButton;

    private final LoginCommand loginCommand = new LoginCommand();
    private final GetShopCommand getShopCommand = new GetShopCommand();

    @FXML
    public void initialize() {
        signInButton.setOnAction(actionEvent -> {
            signIn();
        });
    }

    public void signIn() {
        String username = usernameTextField.getText();
        String password = passwordTextField.getText();
        loginCommand.setPersonIntroduced(new Person(username, password));
        if(!loginCommand.execute()) {
            ViewModel.initAlarmBox("Error", "Invalid username and password!", Alert.AlertType.ERROR);
            return;
        }
        Person personDb = loginCommand.getPerson();
        if(!personDb.getPassword().equals(password)){
            ViewModel.initAlarmBox("Error", "The password is not correct, please try again!", Alert.AlertType.ERROR);
            return;
        }
        if (personDb.getRole().equals(Role.ADMIN)) {
            Callback<Class<?>, Object> controllerFactory = type -> {
                if (type == AdminVM.class) {
                    return new AdminVM();
                } else {
                    try {
                        return type.newInstance();
                    } catch (Exception exc) {
                        ViewModel.initAlarmBox("Error", "Could not load admin controller...", Alert.AlertType.ERROR);
                        throw new RuntimeException(exc.getMessage());
                    }
                }
            };
            ViewModel.loadFXML("/com/example/perfumeshop/admin-view.fxml", controllerFactory);
        } else if (personDb.getRole().equals(Role.MANAGER)) {
            Callback<Class<?>, Object> controllerFactory = type -> {
                if (type == ManagerVM.class) {
                    return new ManagerVM();
                } else {
                    try {
                        return type.newInstance();
                    } catch (Exception exc) {
                        ViewModel.initAlarmBox("Error", "Could not load manager controller...", Alert.AlertType.ERROR);
                        throw new RuntimeException(exc.getMessage());
                    }
                }
            };
            ViewModel.loadFXML("/com/example/perfumeshop/manager-view.fxml", controllerFactory);
        } else {
            Callback<Class<?>, Object> controllerFactory = type -> {
                getShopCommand.setUsername(username);
                if (type == EmployeeVM.class && getShopCommand.execute()) {
                    return new EmployeeVM(getShopCommand.getShopId());
                } else {
                    try {
                        return type.newInstance();
                    } catch (Exception exc) {
                        ViewModel.initAlarmBox("Error", "Could not load employee controller...", Alert.AlertType.ERROR);
                        throw new RuntimeException(exc.getMessage());
                    }
                }
            };
            ViewModel.loadFXML("/com/example/perfumeshop/employee-view.fxml", controllerFactory);
        }
    }
}