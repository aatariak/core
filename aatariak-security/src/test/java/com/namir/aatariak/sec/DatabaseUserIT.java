package com.namir.aatariak.sec;

import org.junit.Test;
import org.junit.Before;
import org.flywaydb.core.Flyway;
import org.junit.runner.RunWith;
import com.namir.aatariak.shared.valueObjects.ID;
import com.namir.aatariak.sec.domain.entity.User;
import org.springframework.jdbc.core.JdbcTemplate;
import org.testcontainers.containers.PostgreSQLContainer;
import com.namir.aatariak.shared.valueObjects.EmailAddress;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import com.namir.aatariak.sec.infrastructure.repository.UserRepository;

import static org.junit.jupiter.api.Assertions.*;


@RunWith(SpringRunner.class) // You need this annotation to just enable spring boot features
@SpringBootTest(classes = {SecTestConfiguration.class}) // This annotation is used to load complete application context for end to end integration testing
public class DatabaseUserIT {

    @Autowired
    private PostgreSQLContainer<?> postgreSQLContainer;

    @Autowired
    protected JdbcTemplate jdbcTemplate;

    @Autowired
    protected PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;


    @Before
    public void setUp() {
        postgreSQLContainer.start();
        Flyway flyway =
                Flyway.configure()
                        .dataSource( postgreSQLContainer.getJdbcUrl(), postgreSQLContainer.getUsername(), postgreSQLContainer.getPassword())
                        .load();

        flyway.migrate();
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
