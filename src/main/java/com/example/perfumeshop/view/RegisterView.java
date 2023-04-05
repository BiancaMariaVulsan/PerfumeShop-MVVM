package com.example.perfumeshop.view;

import com.example.perfumeshop.model.Person;
import com.example.perfumeshop.model.Role;
import com.example.perfumeshop.model.Shop;
import com.example.perfumeshop.view_model.AdminVM;
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
    @FXML
    private TableView personTableView;
    @FXML
    private TableColumn firstNameColumn;
    @FXML
    private TableColumn lastNameColumn;
    @FXML
    private TableColumn roleColumn;

    private boolean isEditing;
    private final RegisterVM registerVM = new RegisterVM();
    private final AdminVM adminVM = new AdminVM();

    public RegisterView() {
        this.isEditing = false;
    }

    public RegisterView(TableView<Person> personTableView,
                      TableColumn<Person, String> firstNameColumn, TableColumn<Person, String> lastNameColumn,
                      TableColumn<Person, String> roleColumn) {
        this.isEditing = false;
        this.personTableView = personTableView;
        this.firstNameColumn = firstNameColumn;
        this.lastNameColumn = lastNameColumn;
        this.roleColumn = roleColumn;
    }

    public void bind() {
        usernameTextField.textProperty().bindBidirectional(registerVM.usernamePropertyProperty());
        firstNameTextField.textProperty().bindBidirectional(registerVM.firstNamePropertyProperty());
        lastNameTextField.textProperty().bindBidirectional(registerVM.lastNamePropertyProperty());
        passwordTextField.textProperty().bindBidirectional(registerVM.passwordPropertyProperty());
        shopChoiceBox.valueProperty().bindBidirectional(registerVM.shopNamePropertyProperty());
        roleChoiceBox.valueProperty().bindBidirectional(registerVM.roleNamePropertyProperty());
        termsCheckBox.selectedProperty().bindBidirectional(registerVM.termsCheckPropertyProperty());
        firstNameColumn.textProperty().bindBidirectional(registerVM.firstNameColumnProperty());
        lastNameColumn.textProperty().bindBidirectional(registerVM.lastNameColumnProperty());
        roleColumn.textProperty().bindBidirectional(registerVM.roleColumnProperty());
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        bind();
        registerButton.setDisable(true);
//        setProgressIndicator();
        initRoleCheckBox();
        initShopCheckBox();

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
            if(adminVM.addPerson()) {
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
        shopChoiceBox.setValue(shops.get(0).getName()); // suppose there is at least one shop
    }

    private void initRoleCheckBox() {
        roleChoiceBox.getItems().add("EMPLOYEE");
        roleChoiceBox.getItems().add("MANAGER");
        roleChoiceBox.getItems().add("ADMIN");
        roleChoiceBox.setValue("EMPLOYEE");
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
