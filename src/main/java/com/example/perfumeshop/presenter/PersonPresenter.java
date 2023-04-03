package com.example.perfumeshop.presenter;

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

    public static boolean addPerson(Person person) {
        try {
            if(person.getRole()!=Role.EMPLOYEE) {
                personPersistence.insert(person);
            } else {
                EmployeePresenter.addPerson((Employee) person);
            }
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    public static boolean deletePersons(Person person) {
        try {
            personPersistence.delete(person);
        } catch (Exception e) {
            return false;
        }
        return true;
    }
}
