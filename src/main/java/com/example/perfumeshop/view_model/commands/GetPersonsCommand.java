package com.example.perfumeshop.view_model.commands;

import com.example.perfumeshop.model.Person;
import com.example.perfumeshop.model.persistence.PersonPersistence;

import java.util.List;

public class GetPersonsCommand implements ICommand {
    private static final PersonPersistence personPersistence = new PersonPersistence();
    List<Person> persons;
    @Override
    public boolean execute() {
        persons = personPersistence.findAll();
        return persons != null;
    }

    public List<Person> getPersons() {
        return persons;
    }
}
