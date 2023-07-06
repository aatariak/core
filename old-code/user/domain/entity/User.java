package com.namir.aatariak.sec.user.user.domain.entity;

import com.namir.aatariak.shared.libs.AggregateRoot;
import com.namir.aatariak.shared.valueObjects.ID;
import com.namir.aatariak.shared.valueObjects.EmailAddress;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

public class User implements AggregateRoot {
    private ID id;
    @NotNull(message = "Name is required")
    private String name;
    private EmailAddress email;
    private String password;
    private List<UserAuthority> userAuthorities = new ArrayList<>();

    public String getId() {
        if (id == null) {
            return null;
        }
        return id.getValue();
    }

    public void setId(ID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email.toString();
    }

    public void setEmail(EmailAddress email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<UserAuthority> getUserAuthorities() {
        return userAuthorities;
    }

    public void setUserAuthorities(List<UserAuthority> userAuthorities) {
        this.userAuthorities = userAuthorities;
    }

    public void addAuthority(String authority) {
        this.userAuthorities.add(new UserAuthority(this, authority));
    }
}
