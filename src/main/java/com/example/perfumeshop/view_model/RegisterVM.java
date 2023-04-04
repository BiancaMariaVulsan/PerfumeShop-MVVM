package com.example.perfumeshop.view_model;

import com.example.perfumeshop.model.Employee;
import com.example.perfumeshop.model.Person;
import com.example.perfumeshop.model.Role;
import com.example.perfumeshop.model.Shop;
import com.example.perfumeshop.view_model.commands.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class RegisterVM implements Initializable
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

    private final AddPersonCommand addPersonCommand = new AddPersonCommand();
    private final UpdatePersonCommand updatePersonCommand = new UpdatePersonCommand();

    public RegisterVM() {
        this.isEditing = false;
    }

    public RegisterVM(Person item, TableView<Person> personTableView, ObservableList<Person> personItems,
                      TableColumn<Person, String> firstNameColumn, TableColumn<Person, String> lastNameColumn,
                      TableColumn<Person, String> roleColumn) {
        this.isEditing = true;
        this.personToUpdate = item;
        this.personTableView = personTableView;
        this.personItems = personItems;
        this.firstNameColumn = firstNameColumn;
        this.lastNameColumn = lastNameColumn;
        this.roleColumn = roleColumn;
    }

    public RegisterVM(TableView<Person> personTableView, ObservableList<Person> personItems,
                      TableColumn<Person, String> firstNameColumn, TableColumn<Person, String> lastNameColumn,
                      TableColumn<Person, String> roleColumn) {
        this.isEditing = false;
        this.personTableView = personTableView;
        this.personItems = personItems;
        this.firstNameColumn = firstNameColumn;
        this.lastNameColumn = lastNameColumn;
        this.roleColumn = roleColumn;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        registerButton.setDisable(true);
        setProgressIndicator();
        initRoleCheckBox();
        initShopCheckBox();

        if(this.isEditing)
        {
            firstNameTextField.setText(personToUpdate.getFirstName());
            lastNameTextField.setText(personToUpdate.getLastName());
            usernameTextField.setText(personToUpdate.getUsername());
            passwordTextField.setText(personToUpdate.getPassword());
            roleChoiceBox.setValue(personToUpdate.getRole());
            shopChoiceBox.setDisable(!personToUpdate.getRole().equals(Role.EMPLOYEE));
        }

        exitButton.setOnAction(actionEvent -> {
            Optional<ButtonType> result = Command.initAlarmBox("Exit", "Are you sure you want to exit? All progress will be lost.", Alert.AlertType.CONFIRMATION);
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
                register();
                Command.populateTablePersons(personTableView, personItems, firstNameColumn, lastNameColumn, roleColumn);
            } else {
                updatePerson(personToUpdate);
                Command.populateTablePersons(personTableView, personItems, firstNameColumn, lastNameColumn, roleColumn);
            }
        });
        roleChoiceBox.getSelectionModel().selectedItemProperty().addListener((options, oldValue, newValue) -> {
            shopChoiceBox.setDisable(!newValue.equals(Role.EMPLOYEE));
        });
    }

    private void register() {
        Role role = roleChoiceBox.getValue();
        Person person;
        if(role.equals(Role.EMPLOYEE)) {
            person = new Employee(firstNameTextField.getText(),
                    lastNameTextField.getText(), usernameTextField.getText(),
                    passwordTextField.getText(), shopChoiceBox.getValue().getId());
        } else {
            person = new Person(firstNameTextField.getText(),
                    lastNameTextField.getText(), role, usernameTextField.getText(),
                    passwordTextField.getText());
        }
        addPersonCommand.setPerson(person);
        if(addPersonCommand.execute()) {
            Command.initAlarmBox("Successful registration", "You are successfully registered!", Alert.AlertType.INFORMATION);
            Stage stage = (Stage) registerButton.getScene().getWindow();
            stage.close();
        } else {
            Command.initAlarmBox("Error", "An error occurred during the registration, please try again!", Alert.AlertType.ERROR);
        }
    }

    public void updatePerson(Person personToUpdate) {
        Role role = roleChoiceBox.getValue();
        if(role.equals(Role.EMPLOYEE)) {
            personToUpdate = new Employee(personToUpdate.getId(), firstNameTextField.getText(),
                    lastNameTextField.getText(), usernameTextField.getText(),
                    passwordTextField.getText(), shopChoiceBox.getValue().getId());
        } else {
            personToUpdate = new Person(personToUpdate.getId(), firstNameTextField.getText(),
                    lastNameTextField.getText(), roleChoiceBox.getValue(),
                    usernameTextField.getText(), passwordTextField.getText());
        }
        updatePersonCommand.setPerson(personToUpdate);
        if(updatePersonCommand.execute()) {
            Command.initAlarmBox("Successful registration", "Person successfully updated!", Alert.AlertType.INFORMATION);
            Stage stage = (Stage) registerButton.getScene().getWindow();
            stage.close();
        } else {
            Command.initAlarmBox("Error", "An error occurred during the registration, please try again!", Alert.AlertType.ERROR);
        }
    }

    private void initRoleCheckBox() {
        roleChoiceBox.getItems().add(Role.EMPLOYEE);
        roleChoiceBox.getItems().add(Role.MANAGER);
        roleChoiceBox.getItems().add(Role.ADMIN);
        roleChoiceBox.setValue(Role.EMPLOYEE);
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

    public void initShopCheckBox() {
        List<Shop> shops = ShopPresenter.getShops();
        for(Shop shop: shops) {
            shopChoiceBox.getItems().add(shop);
        }
        shopChoiceBox.setValue(shops.get(0)); // suppose there is at least one shop
    }
}
