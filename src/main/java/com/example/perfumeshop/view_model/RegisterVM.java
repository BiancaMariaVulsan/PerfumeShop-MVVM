package com.example.perfumeshop.view_model;

import com.example.perfumeshop.model.Employee;
import com.example.perfumeshop.model.Person;
import com.example.perfumeshop.model.Role;
import com.example.perfumeshop.model.Shop;
import com.example.perfumeshop.view_model.commands.*;
import com.example.perfumeshop.view_model.commands.person.AddPersonCommand;
import com.example.perfumeshop.view_model.commands.person.GetRoleByNameCommand;
import com.example.perfumeshop.view_model.commands.person.UpdatePersonCommand;
import javafx.beans.property.*;
import javafx.scene.control.ChoiceBox;

import java.util.List;

public class RegisterVM
{
    private Person personToUpdate;
    private boolean isEditing;
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

    private PersonVM person;

    public RegisterVM() {
        this.isEditing = false;
    }

    public boolean register() {
        AdminVM adminVM = AdminVM.getInstance();
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
                } else {
                    return false;
                }
            }
            addPersonCommand.setPerson(person);
            adminVM.getPersonItems().add(person);
            return addPersonCommand.execute();
        } else {
            this.isEditing = false;
            return updatePerson();
        }
    }

    private boolean updatePerson() {
        AdminVM adminVM = AdminVM.getInstance();
        int index = adminVM.getPersonItems().indexOf(personToUpdate);
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
        if(index > 0) {
            adminVM.getPersonItems().set(index, personToUpdate);
            return updatePersonCommand.execute();
        }
        return false;
    }

    public void initShopCheckBox(ChoiceBox<String> shopChoiceBox) {
        List<Shop> shops = GetShopsCommand.getShops();
        for(Shop shop: shops) {
            shopChoiceBox.getItems().add(shop.getName());
        }
       shopNameProperty.set(shops.get(0).getName()); // suppose there is at least one shop
    }

    public StringProperty usernamePropertyProperty() {
        return usernameProperty;
    }

    public void setUsernameProperty(String usernameProperty) {
        this.usernameProperty.set(usernameProperty);
    }

    public StringProperty firstNamePropertyProperty() {
        return firstNameProperty;
    }

    public void setFirstNameProperty(String firstNameProperty) {
        this.firstNameProperty.set(firstNameProperty);
    }

    public StringProperty lastNamePropertyProperty() {
        return lastNameProperty;
    }

    public void setLastNameProperty(String lastNameProperty) {
        this.lastNameProperty.set(lastNameProperty);
    }

    public StringProperty passwordPropertyProperty() {
        return passwordProperty;
    }

    public void setPasswordProperty(String passwordProperty) {
        this.passwordProperty.set(passwordProperty);
    }

    public StringProperty shopNamePropertyProperty() {
        return shopNameProperty;
    }

    public void setShopNameProperty(String shopNameProperty) {
        this.shopNameProperty.set(shopNameProperty);
    }

    public StringProperty roleNamePropertyProperty() {
        return roleNameProperty;
    }

    public void setRoleNameProperty(String roleNameProperty) {
        this.roleNameProperty.set(roleNameProperty);
    }

    public BooleanProperty termsCheckPropertyProperty() {
        return termsCheckProperty;
    }

    public void setPerson(PersonVM person) {
        AdminVM adminVM = AdminVM.getInstance();
        this.isEditing = true;
        this.person = person;
        this.personToUpdate = adminVM.getPersonItems().stream().filter(p -> p.usernameProperty().get().equals(person.usernameProperty().get())).toList().get(0);
    }
}
