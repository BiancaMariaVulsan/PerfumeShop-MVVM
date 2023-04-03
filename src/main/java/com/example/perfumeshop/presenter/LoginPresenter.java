package com.example.perfumeshop.presenter;

import com.example.perfumeshop.model.Person;
import com.example.perfumeshop.model.Role;
import com.example.perfumeshop.view.*;
import javafx.scene.control.Alert;
import javafx.util.Callback;

public class LoginPresenter { ;
    private final ILoginView loginView;
    public LoginPresenter(ILoginView loginView) {
        this.loginView = loginView;
    }

    public void signIn() {
        String username = loginView.getUsernameTextField().getText();
        String password = loginView.getPasswordTextField().getText();
        Person person = PersonPresenter.getPersonByUsername(username);
        if(person == null) {
            Presenter.initAlarmBox("Error", "Invalid username and password!", Alert.AlertType.ERROR);
            return;
        }
        if(!person.getPassword().equals(password)){
            Presenter.initAlarmBox("Error", "The password is not correct, please try again!", Alert.AlertType.ERROR);
            return;
        }
        if (person.getRole().equals(Role.ADMIN)) {
            Callback<Class<?>, Object> controllerFactory = type -> {
                if (type == AdminView.class) {
                    return new AdminView();
                } else {
                    try {
                        return type.newInstance();
                    } catch (Exception exc) {
                        Presenter.initAlarmBox("Error", "Could not load admin controller...", Alert.AlertType.ERROR);
                        throw new RuntimeException(exc.getMessage());
                    }
                }
            };
            Presenter.loadFXML("/com/example/perfumeshop/admin-view.fxml", controllerFactory);
        } else if (person.getRole().equals(Role.MANAGER)) {
            Callback<Class<?>, Object> controllerFactory = type -> {
                if (type == ManagerView.class) {
                    return new ManagerView();
                } else {
                    try {
                        return type.newInstance();
                    } catch (Exception exc) {
                        Presenter.initAlarmBox("Error", "Could not load manager controller...", Alert.AlertType.ERROR);
                        throw new RuntimeException(exc.getMessage());
                    }
                }
            };
            Presenter.loadFXML("/com/example/perfumeshop/manager-view.fxml", controllerFactory);
        } else {
            Callback<Class<?>, Object> controllerFactory = type -> {
                if (type == EmployeeView.class) {
                    return new EmployeeView(EmployeePresenter.getShopId(username));
                } else {
                    try {
                        return type.newInstance();
                    } catch (Exception exc) {
                        Presenter.initAlarmBox("Error", "Could not load employee controller...", Alert.AlertType.ERROR);
                        throw new RuntimeException(exc.getMessage());
                    }
                }
            };
            Presenter.loadFXML("/com/example/perfumeshop/employee-view.fxml", controllerFactory);
        }
    }
}
