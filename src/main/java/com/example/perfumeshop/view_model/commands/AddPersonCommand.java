package com.example.perfumeshop.view_model.commands;

import com.example.perfumeshop.model.Employee;
import com.example.perfumeshop.model.Person;
import com.example.perfumeshop.model.Role;
import com.example.perfumeshop.model.persistence.EmployeePersistence;
import com.example.perfumeshop.model.persistence.PersonPersistence;

public class AddPersonCommand implements ICommand {
    private Person person;
    private static final PersonPersistence personPersistence = new PersonPersistence();
    private static final EmployeePersistence employeePersistence = new EmployeePersistence();

    @Override
    public boolean execute() {
        try {
            if (person.getRole() != Role.EMPLOYEE) {
                personPersistence.insert(person);
            } else {
                employeePersistence.insert((Employee) person);
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
