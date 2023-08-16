package com.namir.aatariak.sec.config.restFilterChain;

import org.springframework.context.annotation.Bean;
import com.namir.aatariak.sec.config.components.ApiKeyAuthenticationProvider;
import org.springframework.core.annotation.Order;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import static com.namir.aatariak.sec.config.restFilterChain.customDsl.ApiCustomDsl.customDsl;

@Configuration
@ConditionalOnWebApplication
public class VTwoFilterChainConf {
    private ApiKeyAuthenticationProvider apiKeyAuthenticationProvider;

    public VTwoFilterChainConf(
            ApiKeyAuthenticationProvider apiKeyAuthenticationProvider
    )
    {
        this.apiKeyAuthenticationProvider = apiKeyAuthenticationProvider;
    }

    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        source.registerCorsConfiguration("/**", config);
        return new CorsFilter(source);
    }


    @Bean
    @Order(110) // order is important so that more specific matches come before more general ones
    public SecurityFilterChain filterChainAatariak(HttpSecurity http) throws Exception  {
        http
                .securityMatcher("/api/v2/**")
                .apply(customDsl())
                .apiProvider(apiKeyAuthenticationProvider);

        return http.build();
    }
}
