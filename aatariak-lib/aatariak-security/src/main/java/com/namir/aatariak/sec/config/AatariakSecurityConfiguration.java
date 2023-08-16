package com.namir.aatariak.sec.config;

import com.namir.aatariak.sec.config.components.ApiKeyAuthenticationProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.authentication.AnonymousAuthenticationProvider;

@Configuration
public class AatariakSecurityConfiguration {

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    DaoAuthenticationProvider userProvider(UserDetailsService userDetailsService, PasswordEncoder passwordEncoder) {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setUserDetailsService(userDetailsService);
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder);
        return daoAuthenticationProvider;
    }

    @Bean
    AuthenticationManager authManager(
            DaoAuthenticationProvider userProvider,
            ApiKeyAuthenticationProvider apiKeyAuthenticationProvider
    ) {
        ProviderManager providerManager = new ProviderManager(
                apiKeyAuthenticationProvider,
                userProvider,
                new AnonymousAuthenticationProvider("Aatariak")
        );

        return providerManager;
    }

    @Bean
    public JwtAuthenticationConverter jwtAuthenticationConverter() {
//        JwtGrantedAuthoritiesConverter jwtGrantedAuthoritiesConverter = new JwtGrantedAuthoritiesConverter();
//        jwtGrantedAuthoritiesConverter.setAuthoritiesClaimName("aatariak-roles");
//        jwtGrantedAuthoritiesConverter.setAuthorityPrefix("ROLE_");
//
//        JwtAuthenticationConverter jwtAuthenticationConverter = new JwtAuthenticationConverter();
//        jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(jwtGrantedAuthoritiesConverter);

        return new JwtAuthenticationConverter();
    }
}
