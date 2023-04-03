package com.example.perfumeshop.view;

import com.example.perfumeshop.model.Person;
import com.example.perfumeshop.model.Role;
import com.example.perfumeshop.model.Shop;
import com.example.perfumeshop.presenter.IRegisterPresenter;
import com.example.perfumeshop.presenter.Presenter;
import com.example.perfumeshop.presenter.RegisterPresenter;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class RegisterView implements Initializable, IRegisterView
{
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
    private ChoiceBox<Role> roleChoiceBox;
    @FXML
    private ChoiceBox<Shop> shopChoiceBox;
    
    private final IRegisterPresenter registerPresenter;
    private Person personToUpdate;
    private final boolean isEditing;
    @FXML
    private TableView<Person> personTableView;
    private ObservableList<Person> personItems = FXCollections.observableArrayList();
    @FXML
    private TableColumn<Person, String> firstNameColumn;
    @FXML
    private TableColumn<Person, String> lastNameColumn;
    @FXML
    private TableColumn<Person, String> roleColumn;

    public RegisterView() {
        this.isEditing = false;
        this.registerPresenter = new RegisterPresenter(this);
    }

    public RegisterView(Person item, TableView<Person> personTableView, ObservableList<Person> personItems,
                        TableColumn<Person, String> firstNameColumn, TableColumn<Person, String> lastNameColumn,
                        TableColumn<Person, String> roleColumn) {
        this.isEditing = true;
        this.registerPresenter = new RegisterPresenter(this);
        this.personToUpdate = item;
        this.personTableView = personTableView;
        this.personItems = personItems;
        this.firstNameColumn = firstNameColumn;
        this.lastNameColumn = lastNameColumn;
        this.roleColumn = roleColumn;
    }

    public RegisterView(TableView<Person> personTableView, ObservableList<Person> personItems,
                        TableColumn<Person, String> firstNameColumn, TableColumn<Person, String> lastNameColumn,
                        TableColumn<Person, String> roleColumn) {
        this.isEditing = false;
        this.registerPresenter = new RegisterPresenter(this);
        this.personTableView = personTableView;
        this.personItems = personItems;
        this.firstNameColumn = firstNameColumn;
        this.lastNameColumn = lastNameColumn;
        this.roleColumn = roleColumn;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        registerButton.setDisable(true);
        registerPresenter.setProgressIndicator();
        initRoleCheckBox();
        registerPresenter.initShopCheckBox();

        if(this.isEditing)
        {
            firstNameTextField.setText(personToUpdate.getFirstName());
            lastNameTextField.setText(personToUpdate.getLastName());
            usernameTextField.setText(personToUpdate.getUsername());
            passwordTextField.setText(personToUpdate.getPassword());
            roleChoiceBox.setValue(personToUpdate.getRole());
            registerPresenter.enableShopChoiceBox(personToUpdate.getRole());
        }

        exitButton.setOnAction(actionEvent -> {
            Optional<ButtonType> result = Presenter.initAlarmBox("Exit", "Are you sure you want to exit? All progress will be lost.", Alert.AlertType.CONFIRMATION);
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
            if(!isEditing) {
                registerPresenter.register();
                Presenter.populateTablePersons(personTableView, personItems, firstNameColumn, lastNameColumn, roleColumn);
            } else {
                registerPresenter.updatePerson(personToUpdate);
                Presenter.populateTablePersons(personTableView, personItems, firstNameColumn, lastNameColumn, roleColumn);
            }
        });
        roleChoiceBox.getSelectionModel().selectedItemProperty().addListener((options, oldValue, newValue) -> {
            registerPresenter.enableShopChoiceBox(newValue);
        });
    }
    private void initRoleCheckBox() {
        roleChoiceBox.getItems().add(Role.EMPLOYEE);
        roleChoiceBox.getItems().add(Role.MANAGER);
        roleChoiceBox.getItems().add(Role.ADMIN);
        roleChoiceBox.setValue(Role.EMPLOYEE);
    }

    public ProgressIndicator getProgressIndicator() {
        return progressIndicator;
    }

    public TextField getUsernameTextField() {
        return usernameTextField;
    }
    public TextField getPasswordTextField() {
        return passwordTextField;
    }
    public Button getRegisterButton() {
        return registerButton;
    }
    public TextField getFirstNameTextField() {
        return firstNameTextField;
    }
    public TextField getLastNameTextField() {
        return lastNameTextField;
    }
    public ChoiceBox<Role> getRoleChoiceBox() {
        return roleChoiceBox;
    }
    public TableColumn<Person, String> getRoleColumn() {
        return roleColumn;
    }
    public ChoiceBox<Shop> getShopChoiceBox() {
        return shopChoiceBox;
    }
}
