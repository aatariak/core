package com.namir.aatariak.sec.web;

import org.springframework.context.annotation.Bean;
import com.namir.aatariak.sec.ApiKeyAuthenticationProvider;
import org.springframework.core.annotation.Order;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.security.web.authentication.AnonymousAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
@ConditionalOnWebApplication
public class AatariakWebSecurityConfiguration {
    private ApiKeyAuthenticationProvider apiKeyAuthenticationProvider;

    public AatariakWebSecurityConfiguration(
            ApiKeyAuthenticationProvider apiKeyAuthenticationProvider
    )
    {
        this.apiKeyAuthenticationProvider = apiKeyAuthenticationProvider;
    }

    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        // Configure CORS settings
        // config.addAllowedOrigin(...)
        // config.addAllowedHeader(...)
        // config.addAllowedMethod(...)
        source.registerCorsConfiguration("/**", config);
        return new CorsFilter(source);
    }

    @Bean
    @Order(110) // order is important so that more specific matches come before more general ones
    public SecurityFilterChain filterChainAatariak(HttpSecurity http) throws Exception  {

        return http
                .cors()
                .and()
                .csrf().disable()
                .sessionManagement().disable()
                .requestCache().disable()
                .authorizeRequests(auth -> auth
                        .antMatchers("/api/v2/**").authenticated()
                        .anyRequest().permitAll()
                )
                .authenticationManager(new ProviderManager(apiKeyAuthenticationProvider))
                .addFilterBefore(new ApiKeyAuthenticationFilter(), AnonymousAuthenticationFilter.class)
                .build();
    }

}
