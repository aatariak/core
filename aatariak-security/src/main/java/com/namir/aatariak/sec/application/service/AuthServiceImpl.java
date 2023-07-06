package com.namir.aatariak.sec.application.service;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.authentication.AuthenticationManager;
import com.namir.aatariak.sec.infrastructure.domainService.AatariakUserDetails;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

@Service
public class AuthServiceImpl implements AuthService{

    private UserDataAccess userDataAccess;

    private AuthenticationManager authenticationManager;

    public AuthServiceImpl(
            UserDataAccess userDataAccess,
            AuthenticationManager authenticationManager
    ) {
        this.userDataAccess = userDataAccess;
        this.authenticationManager = authenticationManager;
    }

    @Override
    public boolean validateCredentials(String username, String password) {

        try {
            AatariakUserDetails userDetails = this.userDataAccess.loadUserByUsername(username);

            Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, password);
            authentication = authenticationManager.authenticate(authentication);
            SecurityContextHolder.getContext().setAuthentication(authentication);
            return authentication.isAuthenticated();
        } catch (BadCredentialsException | UsernameNotFoundException exception) {
            return false;
        }

    }
}
