package com.namir.aatariak.aatariakauthserver.domain.valueObject;

import com.namir.aatariak.shared.libs.ValueObjectBaseInterface;

import java.util.Objects;

public class Permission implements ValueObjectBaseInterface {

    private final String permission;

    public Permission(String permission) {
        this.permission = validate(permission);
    }

    @Override
    public String toString() {
        return this.permission;
    }

    public static String validate(String permission) {
        if (!isValid(permission)) {
            throw new IllegalArgumentException("Invalid permission: " + permission);
        }
        return permission;
    }

    public static boolean isValid(String permission) {
        return Objects.equals(permission, "Admin") || Objects.equals(permission, "User") || Objects.equals(permission, "Anyone");
    }

    public static Permission createAdminPermission() {
        return new Permission("Admin");
    }

    public static Permission createUserPermission() {
        return new Permission("User");
    }

    public static Permission createAnyonePermission() {
        return new Permission("Anyone");
    }
}
