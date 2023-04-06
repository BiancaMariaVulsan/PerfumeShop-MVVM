package com.example.perfumeshop.view;

import com.example.perfumeshop.model.Role;
import com.example.perfumeshop.model.Shop;
import com.example.perfumeshop.view_model.PersonVM;
import com.example.perfumeshop.view_model.RegisterVM;
import com.example.perfumeshop.view_model.ViewModel;
import com.example.perfumeshop.view_model.commands.ShopPresenter;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class RegisterView implements Initializable {
    @FXML
    private ProgressIndicator progressIndicator;
    @FXML
    private CheckBox termsCheckBox;
    @FXML
    private TextField usernameTextField;
    @FXML
    private TextField passwordTextField;
    @FXML
    private TextField firstNameTextField;
    @FXML
    private TextField lastNameTextField;
    @FXML
    private Button exitButton;
    @FXML
    private Button registerButton;
    @FXML
    private ChoiceBox<String> roleChoiceBox;
    @FXML
    private ChoiceBox<String> shopChoiceBox;

    private boolean isEditing;
    private final RegisterVM registerVM = new RegisterVM();
    private PersonVM personVM;

    public RegisterView() {
        this.isEditing = false;
    }

    public RegisterView(PersonVM personVM) {
        this.isEditing = true;
        this.personVM = personVM;
    }

    public void bind() {
        usernameTextField.textProperty().bindBidirectional(registerVM.usernamePropertyProperty());
        firstNameTextField.textProperty().bindBidirectional(registerVM.firstNamePropertyProperty());
        lastNameTextField.textProperty().bindBidirectional(registerVM.lastNamePropertyProperty());
        passwordTextField.textProperty().bindBidirectional(registerVM.passwordPropertyProperty());
        shopChoiceBox.valueProperty().bindBidirectional(registerVM.shopNamePropertyProperty());
        roleChoiceBox.valueProperty().bindBidirectional(registerVM.roleNamePropertyProperty());
        termsCheckBox.selectedProperty().bindBidirectional(registerVM.termsCheckPropertyProperty());
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        registerButton.setDisable(true);
//        setProgressIndicator();
        initRoleCheckBox();
        initShopCheckBox();
        bind();

        if(isEditing) {
            registerVM.setFirstNameProperty(personVM.firstNameProperty().get());
            registerVM.setLastNameProperty(personVM.lastNameProperty().get());
            registerVM.setUsernameProperty(personVM.usernameProperty().get());
            registerVM.setPasswordProperty(personVM.passwordProperty().get());
//            shopChoiceBox.setDisable(!personVM.roleProperty().get().equals(Role.EMPLOYEE));
        }

        exitButton.setOnAction(actionEvent -> {
            Optional<ButtonType> result = ViewModel.initAlarmBox("Exit", "Are you sure you want to exit? All progress will be lost.", Alert.AlertType.CONFIRMATION);
            if(result.get() == ButtonType.OK) {
                Stage stage = (Stage) registerButton.getScene().getWindow();
                stage.close();
            }
        });
        termsCheckBox.setOnAction(actionEvent -> {
            if (termsCheckBox.isSelected())
            {
                registerButton.setDisable(false);
            }
            else {
                registerButton.setDisable(true);
            }
        });
        registerButton.setOnAction(actionEvent -> {
            if(isEditing) {
                registerVM.setPerson(personVM);
            }
            if(registerVM.register()) {
                ViewModel.initAlarmBox("Successful registration", "Person successfully registered!", Alert.AlertType.INFORMATION);
                Stage stage = (Stage) registerButton.getScene().getWindow();
                stage.close();
            } else {
                ViewModel.initAlarmBox("Error", "An error occurred during the registration, please try again!", Alert.AlertType.ERROR);
            }
        });
        roleChoiceBox.getSelectionModel().selectedItemProperty().addListener((options, oldValue, newValue) -> {
            shopChoiceBox.setDisable(!newValue.equals(Role.EMPLOYEE));
        });
    }

    public void initShopCheckBox() {
        List<Shop> shops = ShopPresenter.getShops();
        for(Shop shop: shops) {
            shopChoiceBox.getItems().add(shop.getName());
        }
        registerVM.setShopNameProperty(shops.get(0).getName()); // suppose there is at least one shop
    }

    private void initRoleCheckBox() {
        roleChoiceBox.getItems().add("EMPLOYEE");
        roleChoiceBox.getItems().add("MANAGER");
        roleChoiceBox.getItems().add("ADMIN");
        registerVM.setRoleNameProperty("EMPLOYEE");
    }

    public void setProgressIndicator() {
        ArrayList<TextField> textFields = new ArrayList<>();
        textFields.add(usernameTextField);
        textFields.add(passwordTextField);
        textFields.add(firstNameTextField);
        textFields.add(lastNameTextField);

        for (TextField textField : textFields) {
            textField.textProperty().addListener((observable, oldValue, newValue) -> {

                int numberOfNonEmpty = 0;
                for (TextField textField1 : textFields)
                    if (!textField1.getText().isEmpty()) {
                        numberOfNonEmpty++;
                    }
                progressIndicator.setProgress((double) numberOfNonEmpty / textFields.size());
            });
        }
    }
}
