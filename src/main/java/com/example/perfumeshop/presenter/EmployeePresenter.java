package com.example.perfumeshop.presenter;

import com.example.perfumeshop.model.Employee;
import com.example.perfumeshop.model.persistence.EmployeePersistence;

public class EmployeePresenter {
    private static final EmployeePersistence employeePersistence = new EmployeePersistence();
    public static boolean addPerson(Employee employee) {
        try {
            employeePersistence.insert(employee);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    public static boolean checkEmployeeExists(String username) {
        return !employeePersistence.checkIfObjectAlreadyExists(username);
    }

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
