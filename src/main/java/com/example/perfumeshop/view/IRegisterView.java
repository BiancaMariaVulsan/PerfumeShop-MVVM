package com.example.perfumeshop.view;

import com.example.perfumeshop.model.Role;
import com.example.perfumeshop.model.Shop;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TextField;

public interface IRegisterView {

    ProgressIndicator getProgressIndicator();
    TextField getUsernameTextField();
    TextField getPasswordTextField();
    Button getRegisterButton();
    TextField getFirstNameTextField();
    TextField getLastNameTextField();
    ChoiceBox<Role> getRoleChoiceBox();
    ChoiceBox<Shop> getShopChoiceBox();
}
