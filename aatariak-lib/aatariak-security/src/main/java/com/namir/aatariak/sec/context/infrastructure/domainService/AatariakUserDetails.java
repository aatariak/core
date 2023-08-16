package com.namir.aatariak.sec.context.infrastructure.domainService;

import com.namir.aatariak.sec.context.domain.entity.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collection;
import java.util.stream.Collectors;

public class AatariakUserDetails implements UserDetails {
    private final User user;

    public AatariakUserDetails(User user) {
        this.user = user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return user.getRoles().stream()
                .map(role -> new SimpleGrantedAuthority("ROLE_" + role.getPermission().toString()))
                .collect(Collectors.toSet());
    }

    @Override
    public String getPassword() {
        return user.getPassword() != null ? user.getPassword() : "";
    }

    @Override
    public String getUsername() {
        return user.getEmail().toString();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return user.isEnabled();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        AatariakUserDetails other = (AatariakUserDetails) obj;
        return user.equals(other.user);
    }

    @Override
    public int hashCode() {
        return user.hashCode();
    }

    @Override
    public String toString() {
        return user.toString();
    }
}
