package com.example.perfumeshop.view_model.commands;

import com.example.perfumeshop.model.Employee;
import com.example.perfumeshop.model.persistence.EmployeePersistence;

public class EmployeePresenter {
    private static final EmployeePersistence employeePersistence = new EmployeePersistence();

    public static int getShopId(String username) {
        Employee employee = employeePersistence.findAll().stream()
                .filter(e -> e.getUsername().equals(username))
                .findFirst()
                .orElse(null);
        if(employee == null) {
            return -1;
        } else {
            return employee.getShopId();
        }
    }
}
