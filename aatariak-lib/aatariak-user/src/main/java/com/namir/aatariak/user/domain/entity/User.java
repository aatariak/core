package com.namir.aatariak.user.domain.entity;


import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.namir.aatariak.shared.valueObjects.EmailAddress;
import com.namir.aatariak.shared.valueObjects.ID;
import com.namir.aatariak.user.domain.valueObject.Permission;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Data
@EqualsAndHashCode()
public class User implements Serializable {
    private ID id;

    @NotNull(message = "Name is required")
    private String name;

    @JsonManagedReference
    private EmailAddress email;

    private String password;

    private Boolean enabled;

    private LocalDateTime dateCreated = LocalDateTime.now();

    private LocalDateTime lastModifiedDate;

    @JsonManagedReference
    private Set<Role> roles = new HashSet<>();

    public void addRole(String role) {
        this.roles.add(new Role(new Permission(role)));
    }

    public boolean isEnabled() {
        return this.enabled;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "(id = " + id + ", username = " + email + ")";
    }
}
