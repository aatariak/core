package com.namir.aatariak.sec;

import org.springframework.security.core.Authentication;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Objects;

@Component
public class ApiKeyAuthenticationProvider implements AuthenticationProvider {
    @Value("${aatariak.security.api-key:}")
    private String serverApiKey;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        if (this.serverApiKey.isBlank() || !(authentication instanceof ApiKeyAuthenticationToken)) {
            return null;
        }
        if (Objects.equals(authentication.getCredentials().toString(), serverApiKey)) {
            HashSet<GrantedAuthority> authorities = new HashSet<>();
            authorities.add(new SimpleGrantedAuthority("ROLE_User"));
            authorities.add(new SimpleGrantedAuthority("ROLE_Admin"));
            authorities.add(new SimpleGrantedAuthority("ROLE_Anyone"));

            ApiKeyAuthenticationToken authenticationToken = new ApiKeyAuthenticationToken(
                    authentication.getCredentials().toString(),
                    "api",
                    authorities
            );
            authenticationToken.setAuthenticated(true);
            return authenticationToken;
        } else {
            throw new BadCredentialsException("Invalid Api Key");
        }
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(ApiKeyAuthenticationToken.class);
    }
}
