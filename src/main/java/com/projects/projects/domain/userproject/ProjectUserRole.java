package com.projects.projects.domain.userproject;

public enum ProjectUserRole {
    OWNER("owner"),
    MANAGER("manager"),
    OPERATOR("operator");

    private String role;

    ProjectUserRole(String role) {
        this.role = role;
    }

    public String getRole() {
        return role;
    }
}
