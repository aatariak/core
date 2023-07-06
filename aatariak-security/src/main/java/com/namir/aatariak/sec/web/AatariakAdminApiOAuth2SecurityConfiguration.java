package com.namir.aatariak.sec.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.savedrequest.NullRequestCache;

@Configuration
@EnableWebSecurity
@ConditionalOnWebApplication
public class AatariakAdminApiOAuth2SecurityConfiguration {

    @Autowired
    private JwtAuthenticationConverter converter;

    @Bean
    @Order(120) // Order is important so that more specific matches come before more general ones
    @ConditionalOnWebApplication
    public SecurityFilterChain filterChainOAuth2AdminApi(HttpSecurity http) throws Exception {
        http
                .cors().and()
                .csrf().disable()
//                .authorizeRequests().and()
//                .anyRequest().authenticated()
                .authorizeRequests(auth -> auth
                        .antMatchers("/api/v1/**").authenticated()
                        .anyRequest().permitAll()
                )
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
                .requestCache()
                .requestCache(new NullRequestCache()).and()
                .oauth2ResourceServer()
                .jwt()
                .jwtAuthenticationConverter(converter);

        return http.build();
    }
}