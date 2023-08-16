package com.namir.aatariak.core;

import org.flywaydb.core.Flyway;
import org.junit.jupiter.api.BeforeAll;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@Testcontainers
public class BaseIntegrationClass {

    @Container
    public static PostgreSQLContainer container = new PostgreSQLContainer<>("postgres:11.1")
            .withDatabaseName("integration-tests-db")
            .withUsername("sa")
            .withPassword("sa");

    @BeforeAll
    public static void setUp() {
        container.withReuse(true);
        container.start();

        Flyway flyway =
                Flyway.configure()
                        .dataSource( container.getJdbcUrl(), container.getUsername(), container.getPassword())
                        .load();

        flyway.migrate();

    }

    @DynamicPropertySource
    public static void overrideProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", container::getJdbcUrl);
        registry.add("spring.datasource.username", container::getUsername);
        registry.add("spring.datasource.password", container::getPassword);
        registry.add("spring.datasource.driver-class-name", container::getDriverClassName);
    }
}
