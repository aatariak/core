package com.namir.aatariak.aatariakauthserver.domain.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.namir.aatariak.aatariakauthserver.domain.valueObject.Permission;
import com.namir.aatariak.shared.valueObjects.ID;
import lombok.Data;

import java.io.Serializable;
import java.util.Objects;

@Data
public class Role implements Serializable {

    private ID id;

    @JsonBackReference
    private User user;

    private Permission permission = Permission.createUserPermission();

    public Role(Permission permission) {
        this.permission = permission;
    }

    public Role(ID id, User user, Permission permission) {
        this.id = id;
        this.user = user;
        this.permission = permission;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Role role = (Role) o;
        return Objects.equals(id, role.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "(id = " + id + ", user = " + (user != null ? user.getEmail() : null) + ", permission = " + permission + ")";
    }
}