package com.namir.aatariak;

import org.flywaydb.core.Flyway;
import org.junit.ClassRule;
import org.junit.runner.RunWith;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;

//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, properties = {
//    "spring.datasource.url=jdbc:tc:postgresql:14-alpine://testcontainers/workshop?TC_INITFUNCTION=com.namir.aatariak.AbstractContainerTestIntegration::initDatabase"
//})
//@ActiveProfiles("test")
//public abstract class AbstractContainerTestIntegration {
//
//    public static void initDatabase(Connection connection) throws SQLException {
//        System.out.println("init database herreeee");
//        Flyway flyway =
//                Flyway.configure()
//                        .dataSource( "jdbc:tc:postgresql:14-alpine://testcontainers/workshop", "user", "password")
//                        .load();
//
//        flyway.migrate();
//    }
//}


@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration(initializers = {containerTestIntegration.Initializer.class})
public abstract class containerTestIntegration {
    @ClassRule
    public static PostgreSQLContainer postgreSQLContainer = new PostgreSQLContainer("postgres:11.1")
            .withDatabaseName("integration-tests-db")
            .withUsername("sa")
            .withPassword("sa");

    public static JdbcTemplate testJdbcTemplate;

    public containerTestIntegration() {
        // create the datasource from the test container
        // and set the jdbcTemplate
        testJdbcTemplate = new JdbcTemplate();
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("org.postgresql.Driver");
        dataSource.setUrl(postgreSQLContainer.getJdbcUrl());
        dataSource.setUsername(postgreSQLContainer.getUsername());
        dataSource.setPassword(postgreSQLContainer.getPassword());

        testJdbcTemplate.setDataSource(dataSource);
    }

    static class Initializer
            implements ApplicationContextInitializer<ConfigurableApplicationContext> {
        public void initialize(ConfigurableApplicationContext configurableApplicationContext) {
            // TODO: run the flyway migrations
            Flyway flyway =
                Flyway.configure()
                        .dataSource( postgreSQLContainer.getJdbcUrl(), postgreSQLContainer.getUsername(), postgreSQLContainer.getPassword())
                        .load();

            flyway.migrate();

        }
    }
}