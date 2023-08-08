package com.namir.aatariak.sec.domain.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.namir.aatariak.sec.domain.valueObject.AatariakPermission;
import com.namir.aatariak.sec.domain.valueObject.Permission;
import lombok.Data;

import java.io.Serializable;
import java.util.Objects;

@Data
public class Role implements Serializable {

    @JsonBackReference
    private User user;

    private Permission permission = Permission.createPassengerPermission();

    public Role(Permission permission) {
        this.permission = permission;
    }

    public Role(User user, Permission permission) {
        this.user = user;
        this.permission = permission;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Role role = (Role) o;
        return Objects.equals(permission, role.permission);
    }

    @Override
    public int hashCode() {
        return Objects.hash(permission);
    }

    @Override
    public String toString() {
        return this.permission.toString();
    }
}