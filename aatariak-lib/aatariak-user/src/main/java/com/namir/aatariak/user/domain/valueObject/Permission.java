package com.namir.aatariak.user.domain.valueObject;

import com.namir.aatariak.shared.libs.ValueObjectBaseInterface;

public class Permission implements ValueObjectBaseInterface {

    private final AatariakPermission permission;

    public Permission(String permission) {
        this.permission = AatariakPermission.valueOf(permission);
    }

    @Override
    public String toString() {
        return this.permission.name();
    }

    public static Permission createAdminPermission() {
        return new Permission(AatariakPermission.Admin.name());
    }

    public static Permission createPassengerPermission() {
        return new Permission(AatariakPermission.Passenger.name());
    }

    public static Permission createDriverPermission() {
        return new Permission(AatariakPermission.Driver.name());
    }
}
