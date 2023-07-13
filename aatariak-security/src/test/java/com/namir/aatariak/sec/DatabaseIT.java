package com.namir.aatariak.sec;

import org.flywaydb.core.Flyway;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import com.namir.aatariak.shared.valueObjects.ID;
import com.namir.aatariak.sec.domain.entity.User;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.testcontainers.containers.PostgreSQLContainer;
import com.namir.aatariak.shared.valueObjects.EmailAddress;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import com.namir.aatariak.sec.infrastructure.repository.UserRepository;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.junit.jupiter.api.Assertions.*;


@Testcontainers
@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = {SecTestConfiguration.class}) // This annotation is used to load complete application context for end to end integration testing
public class DatabaseIT {

    @Autowired
    protected JdbcTemplate jdbcTemplate;

    @Autowired
    protected PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;


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

    @Test
    public void testUserInsertionWithRelation() {
        User user = new User();
        user.setName("mousa");
        user.setEmail(new EmailAddress("faridMousa@gmail.com"));
        user.setPassword(passwordEncoder.encode("passwordtobehashedlater"));
        user.addRole("Admin");

        User savedUser = this.userRepository.create(user);

        assertNotNull(savedUser);
        assertEquals("mousa", savedUser.getName());
        assertEquals("faridMousa@gmail.com", savedUser.getEmail().toString());

        // test the find user by id
        ID id = savedUser.getId();

        User foundUser = this.userRepository.find(id.toString());

        assertNotNull(foundUser);
        assertEquals("mousa", foundUser.getName());
        assertEquals("faridMousa@gmail.com", foundUser.getEmail().toString());
    }
}
