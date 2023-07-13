package com.namir.aatariak.sec;

import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.context.TestConfiguration;

@TestConfiguration
public class SecTestConfiguration {

    @MockBean
    private JwtDecoder jwtDecoder;
}
