package com.example.perfumeshop.view_model;

import com.example.perfumeshop.model.Person;
import com.example.perfumeshop.model.Role;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class PersonVM {
    private final StringProperty firstName;
    private final StringProperty lastName;
    private final StringProperty username;
    private final StringProperty password;
    private final ObjectProperty<Role> role;
    private final Person person;

    public PersonVM(Object person) {
        this.person = (Person) person;
        this.firstName = new SimpleStringProperty(this.person.getFirstName());
        this.lastName = new SimpleStringProperty(this.person.getLastName());
        this.username = new SimpleStringProperty(this.person.getUsername());
        this.password = new SimpleStringProperty(this.person.getPassword());
        this.role = new SimpleObjectProperty<>(this.person.getRole());
    }

    public StringProperty firstNameProperty() {
        return firstName;
    }

    public StringProperty lastNameProperty() {
        return lastName;
    }

    public ObjectProperty<Role> roleProperty() {
        return role;
    }

    public Person getPerson() {
        return person;
    }

    public StringProperty usernameProperty() {
        return username;
    }

    public StringProperty passwordProperty() {
        return password;
    }
}
