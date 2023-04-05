package com.example.perfumeshop.view_model.commands;

import com.example.perfumeshop.model.Role;

public class GetRoleByNameCommand implements ICommand {
    private String roleName;
    private Role role;
    @Override
    public boolean execute() {
        switch (roleName) {
            case "EMPLOYEE" -> {
                role = Role.EMPLOYEE;
                return true;
            }
            case "MANAGER" -> {
                role = Role.MANAGER;
                return true;
            }
            case "ADMIN" -> {
                role = Role.ADMIN;
                return true;
            }
            default -> role = Role.NO_ROLE;
        }
        return false;
    }
    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }
    public Role getRole() {
        return role;
    }
}
