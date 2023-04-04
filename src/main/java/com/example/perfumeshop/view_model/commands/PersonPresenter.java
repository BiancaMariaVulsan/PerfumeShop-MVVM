package com.example.perfumeshop.view_model.commands;

import com.example.perfumeshop.model.Employee;
import com.example.perfumeshop.model.Person;
import com.example.perfumeshop.model.Role;
import com.example.perfumeshop.model.persistence.PersonPersistence;

import java.util.List;

public class PersonPresenter {
    private static final PersonPersistence personPersistence = new PersonPersistence();
    public static List<Person> getPersons() {
        return personPersistence.findAll();
    }

    public static Person getPersonByUsername(String username) {
        return personPersistence.findAll().stream()
                .filter(p -> p.getUsername().equals(username))
                .findFirst()
                .orElse(null);
    }


}
