package com.example.perfumeshop.view_model.commands.person;

import com.example.perfumeshop.model.Employee;
import com.example.perfumeshop.model.Person;
import com.example.perfumeshop.model.Role;
import com.example.perfumeshop.model.persistence.EmployeePersistence;
import com.example.perfumeshop.model.persistence.PersonPersistence;
import com.example.perfumeshop.view_model.commands.ICommand;

public class UpdatePersonCommand implements ICommand {
    private Person person;
    private static final PersonPersistence personPersistence = new PersonPersistence();
    private static final EmployeePersistence employeePersistence = new EmployeePersistence();

    @Override
    public boolean execute() {
        try {
            if(person.getRole().equals(Role.EMPLOYEE)) {
                employeePersistence.update((Employee) person);
            } else {
                personPersistence.update(person);
            }
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    public void setPerson(Person person) {
        this.person = person;
    }
}
