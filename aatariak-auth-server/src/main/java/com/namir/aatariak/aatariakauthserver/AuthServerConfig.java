package com.namir.aatariak.aatariakauthserver;

import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;
import org.springframework.core.annotation.Order;
import org.springframework.context.annotation.Bean;
import com.nimbusds.jose.jwk.source.ImmutableJWKSet;
import org.springframework.security.config.Customizer;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.oauth2.core.OAuth2TokenType;
import org.springframework.security.oauth2.core.oidc.OidcScopes;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.core.oidc.endpoint.OidcParameterNames;
import org.springframework.security.oauth2.server.authorization.config.ClientSettings;
import org.springframework.security.oauth2.server.authorization.config.ProviderSettings;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;
import org.springframework.security.oauth2.server.authorization.token.JwtEncodingContext;
import org.springframework.security.oauth2.server.authorization.token.OAuth2TokenCustomizer;
import com.namir.aatariak.aatariakauthserver.infrastructure.domainService.AatariakUserDetails;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.client.InMemoryRegisteredClientRepository;
import org.springframework.security.config.annotation.web.configuration.OAuth2AuthorizationServerConfiguration;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Configuration
public class AuthServerConfig {

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    @Order(98)
    SecurityFilterChain authorizationServerSecurityFilterChain(HttpSecurity http) throws Exception {
        OAuth2AuthorizationServerConfiguration.applyDefaultSecurity(http);
        return http
            // Redirect to the login page when not authenticated from the authorization endpoint
            .exceptionHandling(exceptions -> {
                exceptions.authenticationEntryPoint(
                        new LoginUrlAuthenticationEntryPoint("/login")
                );
            })
            .build();
    }

    @Bean
    @Order(99)
    public SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
        http
            .authorizeRequests(authorize -> authorize
                    .anyRequest().authenticated()
            ) // Form login handles the redirect to the login page from the authorization server filter chain
            .formLogin(Customizer.withDefaults());
        return http.build();
    }

    @Bean
    public RegisteredClientRepository registeredClientRepository(PasswordEncoder passwordEncoder) {
        RegisteredClient registeredClient = RegisteredClient.withId(UUID.randomUUID().toString())
            .clientId("aatariak")
            .clientSecret(passwordEncoder.encode("p@ssw0rd"))
            .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC)
            .scope(OidcScopes.OPENID)
            .scope("aatariak:manage")
            .clientSettings(ClientSettings.builder().requireAuthorizationConsent(true).build())
            .redirectUri("http://127.0.0.1:3000/api/auth/callback/oauth2")
            .redirectUri("https://oidcdebugger.com/debug")
            .redirectUri("http://auth-server:8080/login/oauth2/code/messaging-client-oidc")
            .redirectUri("http://auth-server:8080/authorized")
            .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
            .authorizationGrantType(AuthorizationGrantType.REFRESH_TOKEN)
            .authorizationGrantType(AuthorizationGrantType.CLIENT_CREDENTIALS)
            .build();

        return new InMemoryRegisteredClientRepository(registeredClient);
    }

    @Bean
    public OAuth2TokenCustomizer<JwtEncodingContext> jwtCustomizer() {
        return context -> {
            if ((OidcParameterNames.ID_TOKEN.equals(context.getTokenType().getValue())
                    || OAuth2TokenType.ACCESS_TOKEN.equals(context.getTokenType()))
                    && context.getAuthorizedScopes().contains("aatariak:manage")) {
                AatariakUserDetails principal = (AatariakUserDetails) context.getPrincipal().getPrincipal();
                List<String> authorities = principal.getUser().getRoles().stream()
                        .map(role -> role.getPermission().toString())
                        .collect(Collectors.toList());
                context.getClaims().claim("aatariak-roles", authorities);
            }
        };
    }

    @Bean
    public ProviderSettings providerSettings() {
        return ProviderSettings.builder()
            .issuer("http://auth-server:8080")
            .build();
    }

    @Bean
    public JWKSource<SecurityContext> jwkSource() {
        KeyPair keyPair = generateRsaKey();
        RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
        RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();

        RSAKey rsaKey = new RSAKey.Builder(publicKey)
                .privateKey(privateKey)
                .keyID(UUID.randomUUID().toString())
                .build();

        JWKSet jwkSet = new JWKSet(rsaKey);
        return new ImmutableJWKSet(jwkSet);
    }

    private KeyPair generateRsaKey() {
        try {
            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
            keyPairGenerator.initialize(2048);
            return keyPairGenerator.generateKeyPair();
        } catch (Exception ex) {
            throw new IllegalStateException(ex);
        }
    }
}
