package com.example.perfumeshop.view_model;

import com.example.perfumeshop.model.Person;
import com.example.perfumeshop.view_model.commands.DeletePersonCommand;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.TableView;

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

//    public void editPerson(TableView<Person> personTableView) {
//        Person item = personTableView.getSelectionModel().getSelectedItem();
//        if(item == null) {
//            ViewModel.initAlarmBox("Warning", "Please select the product to be edited!", Alert.AlertType.WARNING);
//            return;
//        }
//        RegisterVM registerVM = new RegisterVM(item);
//        registerVM.register();
//    }

    public void deletePerson(TableView<Person> personTableView) {
        Person person = personTableView.getSelectionModel().getSelectedItem();
        if(person == null) {
            ViewModel.initAlarmBox("Warning", "Please select the product to be deleted!", Alert.AlertType.WARNING);
            return;
        }
        deletePersonCommand.setPerson(person);
        if(deletePersonCommand.execute()) {
            personItems.remove(person);
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
