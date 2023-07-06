package com.namir.aatariak.sec.user.user.domain.entity;

import com.namir.aatariak.shared.domain.valueObjects.ID;
import org.springframework.lang.NonNull;

public class UserAuthority {
    private ID id;

    private String authority;

    private User user;

    public static final String USER_ROLE = "RIDER";
    public static final String PUBLISHER_ROLE = "DRIVER";

    public UserAuthority() {

    }

    public UserAuthority(User user, String authority) {
        this.user = user;
        this.authority = validate(authority);
    }

    public ID getId() {
        return id;
    }

    public void setId(ID id) {
        this.id = id;
    }

    public String getAuthority() {
        return authority;
    }

    public void setAuthority(String authority) {
        this.authority = validate(authority);
    }

    public static String validate(@NonNull String authority) {
        if(!isValid(authority)) {
            throw new IllegalArgumentException("Invalid authority: " + authority);
        }

        return authority;
    }

    public static Boolean isValid(@NonNull String authority) {
        return authority.equals(USER_ROLE) || authority.equals(PUBLISHER_ROLE);
    }
}
