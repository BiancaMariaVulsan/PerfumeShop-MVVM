package com.example.perfumeshop.view_model.commands.person;

import com.example.perfumeshop.model.Person;
import com.example.perfumeshop.model.persistence.PersonPersistence;
import com.example.perfumeshop.view_model.commands.ICommand;

public class LoginCommand implements ICommand {
    private Person personIntroduced;
    private Person personDb;
    private static final PersonPersistence personPersistence = new PersonPersistence();
    @Override
    public boolean execute() {
        personDb = personPersistence.findAll().stream()
                .filter(p -> p.getUsername().equals(personIntroduced.getUsername()))
                .findFirst()
                .orElse(null);
        return personDb != null;
    }

    public void setPersonIntroduced(Person personIntroduced) {
        this.personIntroduced = personIntroduced;
    }

    public Person getPerson() {
        return personDb;
    }
}
