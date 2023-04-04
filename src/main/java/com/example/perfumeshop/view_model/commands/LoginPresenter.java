package com.example.perfumeshop.view_model.commands;

import com.example.perfumeshop.model.Person;
import com.example.perfumeshop.model.Role;
import com.example.perfumeshop.view_model.*;
import javafx.scene.control.Alert;
import javafx.util.Callback;

public class LoginPresenter { ;
    private final LogInVM loginView;
    public LoginPresenter(LogInVM loginView) {
        this.loginView = loginView;
    }

    public void signIn() {
        String username = loginView.getUsernameTextField().getText();
        String password = loginView.getPasswordTextField().getText();
        Person person = PersonPresenter.getPersonByUsername(username);
        if(person == null) {
            Command.initAlarmBox("Error", "Invalid username and password!", Alert.AlertType.ERROR);
            return;
        }
        if(!person.getPassword().equals(password)){
            Command.initAlarmBox("Error", "The password is not correct, please try again!", Alert.AlertType.ERROR);
            return;
        }
        if (person.getRole().equals(Role.ADMIN)) {
            Callback<Class<?>, Object> controllerFactory = type -> {
                if (type == AdminVM.class) {
                    return new AdminVM();
                } else {
                    try {
                        return type.newInstance();
                    } catch (Exception exc) {
                        Command.initAlarmBox("Error", "Could not load admin controller...", Alert.AlertType.ERROR);
                        throw new RuntimeException(exc.getMessage());
                    }
                }
            };
            Command.loadFXML("/com/example/perfumeshop/admin-view.fxml", controllerFactory);
        } else if (person.getRole().equals(Role.MANAGER)) {
            Callback<Class<?>, Object> controllerFactory = type -> {
                if (type == ManagerVM.class) {
                    return new ManagerVM();
                } else {
                    try {
                        return type.newInstance();
                    } catch (Exception exc) {
                        Command.initAlarmBox("Error", "Could not load manager controller...", Alert.AlertType.ERROR);
                        throw new RuntimeException(exc.getMessage());
                    }
                }
            };
            Command.loadFXML("/com/example/perfumeshop/manager-view.fxml", controllerFactory);
        } else {
            Callback<Class<?>, Object> controllerFactory = type -> {
                if (type == EmployeeVM.class) {
                    return new EmployeeVM(EmployeePresenter.getShopId(username));
                } else {
                    try {
                        return type.newInstance();
                    } catch (Exception exc) {
                        Command.initAlarmBox("Error", "Could not load employee controller...", Alert.AlertType.ERROR);
                        throw new RuntimeException(exc.getMessage());
                    }
                }
            };
            Command.loadFXML("/com/example/perfumeshop/employee-view.fxml", controllerFactory);
        }
    }
}
