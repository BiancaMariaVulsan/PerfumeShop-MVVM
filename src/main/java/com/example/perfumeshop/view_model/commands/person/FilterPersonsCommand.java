package com.example.perfumeshop.view_model.commands.person;

import com.example.perfumeshop.model.Person;
import com.example.perfumeshop.view_model.AdminVM;
import com.example.perfumeshop.view_model.commands.ICommand;

import java.util.List;

public class FilterPersonsCommand implements ICommand {
    private List<Person> persons;
    private String role;

    public FilterPersonsCommand(List<Person> persons, String role) {
        this.persons = persons;
        this.role = role;
    }

    @Override
    public boolean execute() {
        AdminVM adminVM = AdminVM.getInstance();
        adminVM.getPersonItems().setAll(
                persons.stream().filter(p -> p.getRole().name().equals(role)).toList()
        );
        return true;
    }
}
