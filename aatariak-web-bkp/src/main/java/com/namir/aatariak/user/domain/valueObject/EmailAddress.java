package com.namir.aatariak.sec.user.user.domain.valueObject;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.namir.aatariak.libs.ddd.baseClasses.ValueObjectBaseInterface;
import org.springframework.lang.NonNull;

public class EmailAddress implements ValueObjectBaseInterface {
    private final String email;

    public EmailAddress(@NonNull String email) {
        this.email = validate(email);
    }

    @Override
    public String toString() {
        return this.email;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EmailAddress that = (EmailAddress) o;
        return email.equals(that.email);
    }

    public static String validate(@NonNull String email) {
        if (!isValid(email)) {
            throw new IllegalArgumentException("Invalid email: " + email);
        }
        return email;
    }

    public static boolean isValid(@NonNull String email) {
        return true;
    }
}
