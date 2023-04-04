package com.example.perfumeshop.view_model.commands;

import com.example.perfumeshop.model.Person;
import com.example.perfumeshop.model.persistence.PersonPersistence;

public class DeletePersonCommand implements ICommand {
    private Person person;
    private static final PersonPersistence personPersistence = new PersonPersistence();

    @Override
    public boolean execute() {
        try {
            personPersistence.delete(person);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    public void setPerson(Person person) {
        this.person = person;
    }
}
