package com.example.perfumeshop.view_model;

import com.example.perfumeshop.view_model.commands.LoginPresenter;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class LogInVM {
    @FXML
    private TextField usernameTextField;
    @FXML
    private TextField passwordTextField;
    @FXML
    private Button signInButton;

    private final LoginPresenter loginPresenter;

    public LogInVM() {
        this.loginPresenter = new LoginPresenter(this);
    }

    @FXML
    public void initialize() {
        signInButton.setOnAction(actionEvent -> {
            loginPresenter.signIn();
        });
    }

    public TextField getUsernameTextField() {
        return usernameTextField;
    }
    public TextField getPasswordTextField() {
        return passwordTextField;
    }
}