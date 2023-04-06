package com.example.perfumeshop.view_model;

import com.example.perfumeshop.model.Person;
import com.example.perfumeshop.view_model.commands.DeletePersonCommand;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;

import java.util.stream.Collectors;

public class AdminVM {

    private ObjectProperty<String> firstNameColumn = new SimpleObjectProperty<>();
    private ObjectProperty<String> lastNameColumn = new SimpleObjectProperty<>();
    private ObjectProperty<String> roleColumn = new SimpleObjectProperty<>();

    private ObservableList<Person> personItems = FXCollections.observableArrayList();

    private final DeletePersonCommand deletePersonCommand = new DeletePersonCommand();

    private static final AdminVM adminVM = new AdminVM();
    private AdminVM() {

    }
    public static AdminVM getInstance() {
        return adminVM;
    }

    public void deletePerson(PersonVM person) {
        Person personToDel = personItems.stream().filter(p -> p.usernameProperty().get().equals(person.usernameProperty().get())).toList().get(0);
        deletePersonCommand.setPerson(personToDel);
        if(deletePersonCommand.execute()) {
            personItems.remove(personToDel);
        } else {
            ViewModel.initAlarmBox("Warning", "Delete operation failed, please try again!", Alert.AlertType.WARNING);
        }
    }

    public String getFirstNameColumn() {
        return firstNameColumn.get();
    }

    public ObjectProperty<String> firstNameColumnProperty() {
        return firstNameColumn;
    }

    public void setFirstNameColumn(String firstNameColumn) {
        this.firstNameColumn.set(firstNameColumn);
    }

    public String getLastNameColumn() {
        return lastNameColumn.get();
    }

    public ObjectProperty<String> lastNameColumnProperty() {
        return lastNameColumn;
    }

    public void setLastNameColumn(String lastNameColumn) {
        this.lastNameColumn.set(lastNameColumn);
    }

    public String getRoleColumn() {
        return roleColumn.get();
    }

    public ObjectProperty<String> roleColumnProperty() {
        return roleColumn;
    }

    public void setRoleColumn(String roleColumn) {
        this.roleColumn.set(roleColumn);
    }

    public ObservableList<Person> getPersonItems() {
        return personItems;
    }

    public void setPersonItems(ObservableList<Person> personItems) {
        this.personItems = personItems;
    }
}
