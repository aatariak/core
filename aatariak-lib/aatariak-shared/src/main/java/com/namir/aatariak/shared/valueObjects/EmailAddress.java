package com.namir.aatariak.shared.valueObjects;

import com.namir.aatariak.shared.libs.ValueObjectBaseInterface;

public class EmailAddress implements ValueObjectBaseInterface {
    private final String email;

    public EmailAddress(String email) {
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

    public static String validate(String email) {
        if (!isValid(email)) {
            throw new IllegalArgumentException("Invalid email: " + email);
        }
        return email;
    }

    public static boolean isValid(String email) {
        return true;
    }


}
