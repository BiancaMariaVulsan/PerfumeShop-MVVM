package com.example.perfumeshop.presenter;

import com.example.perfumeshop.model.Employee;
import com.example.perfumeshop.model.Person;
import com.example.perfumeshop.model.Role;
import com.example.perfumeshop.model.Shop;
import com.example.perfumeshop.model.persistence.EmployeePersistence;
import com.example.perfumeshop.model.persistence.PersonPersistence;
import com.example.perfumeshop.view.IRegisterView;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

public class RegisterPresenter implements IRegisterPresenter {
    private final IRegisterView registerView;

    public RegisterPresenter(IRegisterView registerView) {
        this.registerView = registerView;
    }
    @Override
    public void setProgressIndicator() {
        ArrayList<TextField> textFields = new ArrayList<>();
        textFields.add(registerView.getUsernameTextField());
        textFields.add(registerView.getPasswordTextField());
        textFields.add(registerView.getFirstNameTextField());
        textFields.add(registerView.getLastNameTextField());

        for (TextField textField : textFields) {
            textField.textProperty().addListener((observable, oldValue, newValue) -> {

                int numberOfNonEmpty = 0;
                for (TextField textField1 : textFields)
                    if (!textField1.getText().isEmpty()) {
                        numberOfNonEmpty++;
                    }
                registerView.getProgressIndicator().setProgress((double) numberOfNonEmpty / textFields.size());
            });
        }
    }
    @Override
    public void register() {
        Role role = registerView.getRoleChoiceBox().getValue();
        Person person;
        if(role.equals(Role.EMPLOYEE)) {
            person = new Employee(registerView.getFirstNameTextField().getText(),
                    registerView.getLastNameTextField().getText(), registerView.getUsernameTextField().getText(),
                    registerView.getPasswordTextField().getText(), registerView.getShopChoiceBox().getValue().getId());
        } else {
            person = new Person(registerView.getFirstNameTextField().getText(),
                    registerView.getLastNameTextField().getText(), role, registerView.getUsernameTextField().getText(),
                    registerView.getPasswordTextField().getText());
        }
        if(PersonPresenter.addPerson(person)) {
            Presenter.initAlarmBox("Successful registration", "You are successfully registered!", Alert.AlertType.INFORMATION);
            Stage stage = (Stage) registerView.getRegisterButton().getScene().getWindow();
            stage.close();
        } else {
            Presenter.initAlarmBox("Error", "An error occurred during the registration, please try again!", Alert.AlertType.ERROR);
        }
    }

    @Override
    public void updatePerson(Person personToUpdate) {
        Role role = registerView.getRoleChoiceBox().getValue();
        PersonPersistence personPersistence = new PersonPersistence();
        EmployeePersistence employeePersistence = new EmployeePersistence();
        boolean successUpdate = false;
        if(role.equals(Role.EMPLOYEE)) {
            personToUpdate = new Employee(personToUpdate.getId(), registerView.getFirstNameTextField().getText(),
                    registerView.getLastNameTextField().getText(), registerView.getUsernameTextField().getText(),
                    registerView.getPasswordTextField().getText(), registerView.getShopChoiceBox().getValue().getId());
            successUpdate = employeePersistence.update((Employee) personToUpdate);
        } else {
            personToUpdate = new Person(personToUpdate.getId(), registerView.getFirstNameTextField().getText(),
                    registerView.getLastNameTextField().getText(), registerView.getRoleChoiceBox().getValue(),
                    registerView.getUsernameTextField().getText(), registerView.getPasswordTextField().getText());
            successUpdate = personPersistence.update(personToUpdate);
        }
        if(successUpdate) {
            Presenter.initAlarmBox("Successful registration", "Person successfully updated!", Alert.AlertType.INFORMATION);
            Stage stage = (Stage) registerView.getRegisterButton().getScene().getWindow();
            stage.close();
        } else {
            Presenter.initAlarmBox("Error", "An error occurred during the registration, please try again!", Alert.AlertType.ERROR);
        }
    }
    @Override
    public void initShopCheckBox() {
        List<Shop> shops = ShopPresenter.getShops();
        for(Shop shop: shops) {
            registerView.getShopChoiceBox().getItems().add(shop);
        }
        registerView.getShopChoiceBox().setValue(shops.get(0)); // suppose there is at least one shop
    }
    @Override
    public void enableShopChoiceBox(Role role) {
        if(role.equals(Role.EMPLOYEE)) {
            registerView.getShopChoiceBox().setDisable(false);
        } else {
            registerView.getShopChoiceBox().setDisable(true);
        }
    }
}
