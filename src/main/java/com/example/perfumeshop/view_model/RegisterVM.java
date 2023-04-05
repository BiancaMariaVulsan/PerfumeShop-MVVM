package com.example.perfumeshop.view_model;

import com.example.perfumeshop.model.Employee;
import com.example.perfumeshop.model.Person;
import com.example.perfumeshop.model.Role;
import com.example.perfumeshop.view_model.commands.AddPersonCommand;
import com.example.perfumeshop.view_model.commands.GetRoleByNameCommand;
import com.example.perfumeshop.view_model.commands.GetShopIdByNameCommand;
import com.example.perfumeshop.view_model.commands.UpdatePersonCommand;
import javafx.beans.property.*;
import javafx.collections.ObservableList;

public class RegisterVM
{
    private Person personToUpdate;
    private final boolean isEditing;

    private ObservableList<Person> personItems;
    private final AddPersonCommand addPersonCommand = new AddPersonCommand();
    private final UpdatePersonCommand updatePersonCommand = new UpdatePersonCommand();
    private final GetRoleByNameCommand roleCommand = new GetRoleByNameCommand();
    private final GetShopIdByNameCommand shopCommand = new GetShopIdByNameCommand();

    private ViewModel viewModel = new ViewModel();

    private StringProperty usernameProperty = new SimpleStringProperty();
    private StringProperty firstNameProperty = new SimpleStringProperty();
    private StringProperty lastNameProperty = new SimpleStringProperty();
    private StringProperty passwordProperty = new SimpleStringProperty();
    private StringProperty shopNameProperty = new SimpleStringProperty();
    private StringProperty roleNameProperty = new SimpleStringProperty();
    private BooleanProperty termsCheckProperty = new SimpleBooleanProperty();
    private ObjectProperty<String> firstNameColumn = new SimpleObjectProperty<>();
    private ObjectProperty<String> lastNameColumn = new SimpleObjectProperty<>();
    private ObjectProperty<String> roleColumn = new SimpleObjectProperty<>();

    public RegisterVM() {
        this.isEditing = false;
    }
    public RegisterVM(ObservableList<Person> personItems) {
        this.isEditing = false;
        this.personItems = personItems;
    }
    public RegisterVM(Person personToUpdate, ObservableList<Person> personItems) {
        this.personItems = personItems;
        this.personToUpdate = personToUpdate;
        this.isEditing = true;
        firstNameProperty.set(personToUpdate.getFirstName());
        lastNameProperty.set(personToUpdate.getLastName());
        usernameProperty.set(personToUpdate.getUsername());
        passwordProperty.set(personToUpdate.getPassword());
        roleNameProperty.set(personToUpdate.getRole().name());
//            shopChoiceBox.setDisable(!personToUpdate.getRole().equals(Role.EMPLOYEE));
    }

    public boolean register() {
        if(!isEditing) {
            Person person;
            if(roleNameProperty.get().equals("EMPLOYEE")) {
                shopCommand.setShopName(shopNameProperty.get());
                if(shopCommand.execute()) {
                    person = new Employee(firstNameProperty.get(),
                            lastNameProperty.get(), usernameProperty.get(),
                            passwordProperty.get(), shopCommand.getShopId());
                } else {
                    return false;
                }
            } else {
                roleCommand.setRoleName(roleNameProperty.get());
                if (roleCommand.execute()) {
                    Role role = roleCommand.getRole();
                    person = new Person(firstNameProperty.get(),
                            lastNameProperty.get(), role, usernameProperty.get(),
                            passwordProperty.get());
                    addPersonCommand.setPerson(person);
                    personItems.add(person);
                    return addPersonCommand.execute();
                } else {
                    return false;
                }
            }
        } else {
            return updatePerson();
        }
        return false;
    }

    private boolean updatePerson() {
        if(roleNameProperty.get().equals("EMPLOYEE")) {
            shopCommand.setShopName(shopNameProperty.get());
            if(shopCommand.execute()) {
                personToUpdate = new Employee(personToUpdate.getId(), firstNameProperty.get(),
                        lastNameProperty.get(), usernameProperty.get(),
                        passwordProperty.get(), shopCommand.getShopId());
            } else {
                return false;
            }
        } else {
            roleCommand.setRoleName(roleNameProperty.get());
            roleCommand.execute();
            personToUpdate = new Person(personToUpdate.getId(), firstNameProperty.get(),
                    lastNameProperty.get(), roleCommand.getRole(),
                    usernameProperty.get(), passwordProperty.get());
        }
        updatePersonCommand.setPerson(personToUpdate);
        return updatePersonCommand.execute();
    }

    public String getUsernameProperty() {
        return usernameProperty.get();
    }

    public StringProperty usernamePropertyProperty() {
        return usernameProperty;
    }

    public void setUsernameProperty(String usernameProperty) {
        this.usernameProperty.set(usernameProperty);
    }

    public String getFirstNameProperty() {
        return firstNameProperty.get();
    }

    public StringProperty firstNamePropertyProperty() {
        return firstNameProperty;
    }

    public void setFirstNameProperty(String firstNameProperty) {
        this.firstNameProperty.set(firstNameProperty);
    }

    public String getLastNameProperty() {
        return lastNameProperty.get();
    }

    public StringProperty lastNamePropertyProperty() {
        return lastNameProperty;
    }

    public void setLastNameProperty(String lastNameProperty) {
        this.lastNameProperty.set(lastNameProperty);
    }

    public String getPasswordProperty() {
        return passwordProperty.get();
    }

    public StringProperty passwordPropertyProperty() {
        return passwordProperty;
    }

    public void setPasswordProperty(String passwordProperty) {
        this.passwordProperty.set(passwordProperty);
    }

    public String getShopNameProperty() {
        return shopNameProperty.get();
    }

    public StringProperty shopNamePropertyProperty() {
        return shopNameProperty;
    }

    public void setShopNameProperty(String shopNameProperty) {
        this.shopNameProperty.set(shopNameProperty);
    }

    public String getRoleNameProperty() {
        return roleNameProperty.get();
    }

    public StringProperty roleNamePropertyProperty() {
        return roleNameProperty;
    }

    public void setRoleNameProperty(String roleNameProperty) {
        this.roleNameProperty.set(roleNameProperty);
    }

    public boolean isTermsCheckProperty() {
        return termsCheckProperty.get();
    }

    public BooleanProperty termsCheckPropertyProperty() {
        return termsCheckProperty;
    }

    public void setTermsCheckProperty(boolean termsCheckProperty) {
        this.termsCheckProperty.set(termsCheckProperty);
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
}
