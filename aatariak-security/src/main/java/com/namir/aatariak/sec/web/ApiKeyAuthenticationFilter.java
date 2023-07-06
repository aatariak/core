package com.namir.aatariak.sec.web;

import com.namir.aatariak.sec.ApiKeyAuthenticationToken;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ApiKeyAuthenticationFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String xApiKey = request.getHeader("X-Api-Key");
        if (xApiKey == null) {
            response.sendError(HttpStatus.UNAUTHORIZED.value(), "No Token Provided");
            return;
        }

        ApiKeyAuthenticationToken authentication = new ApiKeyAuthenticationToken(xApiKey);

        SecurityContextHolder.getContext().setAuthentication(authentication);

        filterChain.doFilter(request, response);
    }
}
