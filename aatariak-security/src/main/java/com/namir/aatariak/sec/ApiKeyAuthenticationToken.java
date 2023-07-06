package com.namir.aatariak.sec;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.authentication.AbstractAuthenticationToken;

import java.util.Collection;

public class ApiKeyAuthenticationToken extends AbstractAuthenticationToken {
    private String apiKey;
    private String username;

    public ApiKeyAuthenticationToken(
            String apiKey,
            String username,
            Collection<? extends GrantedAuthority> authorities
    ) {
        super(authorities);
        this.apiKey = apiKey;
        this.username = username;
    }

    public ApiKeyAuthenticationToken(
            String apiKey
    ) {
        super(null);
        this.apiKey = apiKey;
        this.username = null;
    }

    @Override
    public Object getCredentials() {
        return this.apiKey;
    }

    @Override
    public Object getPrincipal() {
        return this.username;
    }
}
