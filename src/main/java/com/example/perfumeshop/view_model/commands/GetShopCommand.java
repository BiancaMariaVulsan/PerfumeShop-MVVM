package com.example.perfumeshop.view_model.commands;

import com.example.perfumeshop.model.Employee;
import com.example.perfumeshop.model.persistence.EmployeePersistence;

public class GetShopCommand implements ICommand {
    private String username;
    private int shopId;
    private static final EmployeePersistence employeePersistence = new EmployeePersistence();

    @Override
    public boolean execute() {
        Employee employee = employeePersistence.findAll().stream()
                .filter(e -> e.getUsername().equals(username))
                .findFirst()
                .orElse(null);
        if(employee == null) {
            return false;
        } else {
            shopId = employee.getShopId();
            return true;
        }
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getShopId() {
        return shopId;
    }

}
