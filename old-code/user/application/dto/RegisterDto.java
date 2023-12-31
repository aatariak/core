package com.namir.aatariak.sec.user.user.application.dto;


import com.namir.aatariak.shared.valueObjects.EmailAddress;

import javax.validation.constraints.NotNull;

public class RegisterDto {
    @NotNull(message = "Name is required")
    private String name;
    private String email;
    private String password;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public EmailAddress getEmail() {
        return new EmailAddress(email);
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
