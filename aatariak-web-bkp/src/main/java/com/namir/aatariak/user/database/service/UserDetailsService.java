package com.namir.aatariak.sec.user.user.database.service;

import com.namir.aatariak.shared.application.DTO.RegisterDto;
import org.springframework.stereotype.Component;
import com.namir.aatariak.sec.user.user.domain.entity.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.core.GrantedAuthority;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.namir.aatariak.sec.user.user.domain.valueObject.EmailAddress;
import org.springframework.security.core.userdetails.UserDetails;
import com.namir.aatariak.sec.user.user.database.repository.UserRepository;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.MethodArgumentNotValidException;

import javax.annotation.PostConstruct;
import java.util.Collection;
import java.util.Random;
import java.util.stream.Collectors;

@Component
public class UserDetailsService implements org.springframework.security.core.userdetails.UserDetailsService {
    private final UserRepository users;

    public UserDetailsService(UserRepository users) {
        this.users = users;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = this.users.getUserByEmail(new EmailAddress(username));

        if (user == null) {
            throw new UsernameNotFoundException(username);
        }
        ObjectMapper mapper = new ObjectMapper();
        mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);

        return mapper.convertValue(user, BridgeUser.class);
    }

    private static class BridgeUser extends User implements UserDetails {

        @Override
        public Collection<? extends GrantedAuthority> getAuthorities() {
            return super.getUserAuthorities().stream()
                    .map(userAuthority -> new SimpleGrantedAuthority(userAuthority.getAuthority()))
                    .collect(Collectors.toList());
        }

        @Override
        public String getUsername() {
            return this.getEmail();
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
            return true;
        }
    }
}
