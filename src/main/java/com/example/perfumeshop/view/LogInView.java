package com.example.perfumeshop.view;

import com.example.perfumeshop.presenter.LoginPresenter;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class LogInView implements ILoginView {
    @FXML
    private TextField usernameTextField;
    @FXML
    private TextField passwordTextField;
    @FXML
    private Button signInButton;

    private final LoginPresenter loginPresenter;

    public LogInView() {
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