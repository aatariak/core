package com.namir.aatariak.sec.config.restFilterChain.customDsl;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import org.springframework.http.HttpStatus;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import com.namir.aatariak.sec.config.components.ApiKeyAuthenticationToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.security.core.context.SecurityContextHolder;

import java.io.IOException;

public class ApiKeyAuthenticationFilter extends OncePerRequestFilter {

    private final AuthenticationManager authenticationManager;

    public ApiKeyAuthenticationFilter(
            AuthenticationManager authenticationManager
    ) {
        this.authenticationManager = authenticationManager;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String xApiKey = request.getHeader("X-Api-Key");
        if (xApiKey == null) {
            response.sendError(HttpStatus.UNAUTHORIZED.value(), "No Token Provided");
            return;
        }

        try {
            ApiKeyAuthenticationToken authentication = new ApiKeyAuthenticationToken(xApiKey);

            Authentication authenticated = authenticationManager.authenticate(authentication);

            SecurityContextHolder.getContext().setAuthentication(authenticated);

            filterChain.doFilter(request, response);

        } catch (BadCredentialsException e) {
            response.sendError(HttpStatus.UNAUTHORIZED.value(), e.getMessage());
        }
    }
}
