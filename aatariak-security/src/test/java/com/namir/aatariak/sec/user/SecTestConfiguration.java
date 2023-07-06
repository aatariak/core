package com.namir.aatariak.sec.user;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.testcontainers.containers.PostgreSQLContainer;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

@TestConfiguration
public class SecTestConfiguration {

    @Bean
    @Primary
    public PostgreSQLContainer<?> postgreSQLContainer() {
        PostgreSQLContainer<?> container = new PostgreSQLContainer<>("postgres:11.1")
                .withDatabaseName("integration-tests-db")
                .withUsername("sa")
                .withPassword("sa");

        container.start();
        return container;
    }

    @Bean
    public DriverManagerDataSource driverManagerDataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(postgreSQLContainer().getDriverClassName());
        dataSource.setUrl(postgreSQLContainer().getJdbcUrl());
        dataSource.setUsername(postgreSQLContainer().getUsername());
        dataSource.setPassword(postgreSQLContainer().getPassword());
        return dataSource;
    }

    @MockBean
    private JwtDecoder jwtDecoder;
}
