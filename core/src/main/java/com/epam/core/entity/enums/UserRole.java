package com.epam.core.entity.enums;

public enum UserRole {

    ADMIN("admin"),
    USER("user"),
    GUEST("guest");

    private final String name;

    UserRole(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return this.name;
    }

    public static UserRole getUserRole(String stringUserRole) {
        for (UserRole userRole : values()) {
            if (userRole.name.equals(stringUserRole)) {
                return userRole;
            }
        }
        throw new IllegalArgumentException("Illegal user role : " + stringUserRole);
    }

}
