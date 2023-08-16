package com.namir.aatariak.sec.context.domain.entity;


import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.namir.aatariak.sec.context.domain.valueObject.Permission;
import com.namir.aatariak.shared.valueObjects.EmailAddress;
import com.namir.aatariak.shared.valueObjects.ID;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Data
@EqualsAndHashCode()
public class User implements Serializable {
    private ID id;

    @JsonManagedReference
    private EmailAddress email;

    private String password;

    private Boolean enabled;

    @JsonManagedReference
    private Set<Role> roles = new HashSet<>();

    public void addRole(String role) {
        this.roles.add(new Role(new Permission(role)));
    }

    public boolean isEnabled() {
        return this.enabled;
    }
}
