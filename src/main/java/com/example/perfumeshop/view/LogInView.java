package com.example.perfumeshop.view;

import com.example.perfumeshop.view_model.LogInVM;
import com.example.perfumeshop.view_model.ViewModel;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.util.Callback;

public class LogInView {
    @FXML
    private TextField usernameTextField;
    @FXML
    private TextField passwordTextField;
    @FXML
    private Button signInButton;

    private final LogInVM logInVM = new LogInVM();

    void bind() {
        usernameTextField.textProperty().bindBidirectional(logInVM.usernamePropertyProperty());
        passwordTextField.textProperty().bindBidirectional(logInVM.passwordPropertyProperty());
    }

    @FXML
    public void initialize() {
        bind();
        signInButton.setOnAction(actionEvent -> {
            signIn();
        });
    }

    public void signIn() {
        if (logInVM.login()) {
            if (logInVM.isAdmin()) {
                Callback<Class<?>, Object> controllerFactory = type -> {
                    if (type == AdminView.class) {
                        return new AdminView();
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
            } else if (logInVM.isManager()) {
                Callback<Class<?>, Object> controllerFactory = type -> {
                    if (type == ManagerView.class) {
                        return new ManagerView();
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
            } else if (logInVM.isEmployee()) {
                Callback<Class<?>, Object> controllerFactory = type -> {
                    int id = logInVM.getShopId();
                    if (type == EmployeeView.class && id != -1) {
                        return new EmployeeView(id);
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
}