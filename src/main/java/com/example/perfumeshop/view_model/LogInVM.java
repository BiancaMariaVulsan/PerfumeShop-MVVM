package com.example.perfumeshop.view_model;

import com.example.perfumeshop.model.Person;
import com.example.perfumeshop.model.Role;
import com.example.perfumeshop.view_model.commands.GetShopCommand;
import com.example.perfumeshop.view_model.commands.LoginCommand;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.control.Alert;

public class LogInVM {
    private final StringProperty usernameProperty = new SimpleStringProperty();
    private final StringProperty passwordProperty = new SimpleStringProperty();
    private final LoginCommand loginCommand = new LoginCommand();
    private final GetShopCommand getShopCommand = new GetShopCommand();

    Person personDb;

    public boolean login() {
        loginCommand.setPersonIntroduced(new Person(usernameProperty.get(), passwordProperty.get()));
        if(!loginCommand.execute()) {
            ViewModel.initAlarmBox("Error", "Invalid username and password!", Alert.AlertType.ERROR);
            return false;
        }
        personDb = loginCommand.getPerson();
        if(!personDb.getPassword().equals(passwordProperty.get())){
            ViewModel.initAlarmBox("Error", "The password is not correct, please try again!", Alert.AlertType.ERROR);
            return false;
        }
        return true;
    }

    public boolean isAdmin() {
        return personDb.getRole().equals(Role.ADMIN);
    }

    public boolean isManager() {
        return personDb.getRole().equals(Role.MANAGER);
    }

    public boolean isEmployee() {
        return personDb.getRole().equals(Role.EMPLOYEE);
    }

    public int getShopId() {
    getShopCommand.setUsername(usernameProperty.get());
        if (getShopCommand.execute()) {
            return getShopCommand.getShopId();
        }
        return -1;
    }

    public StringProperty passwordPropertyProperty() {
        return passwordProperty;
    }

    public StringProperty usernamePropertyProperty() {
        return usernameProperty;
    }
}
