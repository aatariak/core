package com.namir.aatariak.sec.config.restFilterChain.customDsl;

import com.namir.aatariak.sec.config.components.ApiKeyAuthenticationProvider;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.RequestCacheConfigurer;
import org.springframework.security.web.authentication.AnonymousAuthenticationFilter;

import static org.springframework.security.config.Customizer.withDefaults;

public class ApiCustomDsl extends AbstractHttpConfigurer<ApiCustomDsl, HttpSecurity> {


    private ApiKeyAuthenticationProvider apiKeyAuthenticationProvider;


    public static ApiCustomDsl customDsl() {
        return new ApiCustomDsl();
    }

    public ApiCustomDsl apiProvider(ApiKeyAuthenticationProvider apiKeyAuthenticationProvider) {
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
        AuthenticationManager authenticationManager = new ProviderManager(apiKeyAuthenticationProvider);

        http
            .addFilterBefore(new ApiKeyAuthenticationFilter(authenticationManager), AnonymousAuthenticationFilter.class);
    }

}
