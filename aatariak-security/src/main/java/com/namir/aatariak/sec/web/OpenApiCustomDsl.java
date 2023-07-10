package com.namir.aatariak.sec.web;

import com.namir.aatariak.sec.ApiKeyAuthenticationProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.RequestCacheConfigurer;
import org.springframework.security.web.authentication.AnonymousAuthenticationFilter;

import static org.springframework.security.config.Customizer.withDefaults;

public class OpenApiCustomDsl extends AbstractHttpConfigurer<OpenApiCustomDsl, HttpSecurity> {


    private ApiKeyAuthenticationProvider apiKeyAuthenticationProvider;


    public static OpenApiCustomDsl customDsl() {
        return new OpenApiCustomDsl();
    }

    public OpenApiCustomDsl apiProvider(ApiKeyAuthenticationProvider apiKeyAuthenticationProvider) {
        this.apiKeyAuthenticationProvider = apiKeyAuthenticationProvider;
        return this;
    }


    @Override
    public void init(HttpSecurity http) throws Exception {
        // any method that adds another configurer
        // must be done in the init method
        http.cors(withDefaults())
            .csrf(AbstractHttpConfigurer::disable)
            .sessionManagement(AbstractHttpConfigurer::disable)
            .requestCache(RequestCacheConfigurer::disable);


    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
//        AuthenticationManager authenticationManager = http.getSharedObject(AuthenticationManager.class);

        AuthenticationManager authenticationManager = new ProviderManager(apiKeyAuthenticationProvider);

        http
            .addFilterBefore(new ApiKeyAuthenticationFilter(authenticationManager), AnonymousAuthenticationFilter.class);
    }

}
