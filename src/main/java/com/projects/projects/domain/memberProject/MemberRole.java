package com.projects.projects.domain.memberProject;

public enum MemberRole {
    OWNER("owner"),
    MANAGER("manager"),
    OPERATOR("operator");

    private String role;

    MemberRole(String role) {
        this.role = role;
    }

    public String getRole() {
        return role;
    }
}
