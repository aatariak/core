package com.namir.aatariak.core;

import com.namir.aatariak.shared.valueObjects.EmailAddress;
import com.namir.aatariak.shared.valueObjects.ID;
import com.namir.aatariak.user.AatariakUserApplication;
import com.namir.aatariak.user.domain.entity.User;
import com.namir.aatariak.user.infrastructure.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit.jupiter.SpringExtension;


@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = {AatariakUserApplication.class, TestConfiguration.class})
public class UserDatabaseIT extends BaseIntegrationClass {

    @Autowired
    protected PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;


    @Test
    public void testUserInsertionWithRelation() {
        User user = new User();
        user.setName("mousa");
        user.setEmail(new EmailAddress("faridMousa@gmail.com"));
        user.setPassword(passwordEncoder.encode("passwordtobehashedlater"));
        user.addRole("Admin");

        User savedUser = this.userRepository.create(user);

        Assertions.assertNotNull(savedUser);
        Assertions.assertEquals("mousa", savedUser.getName());
        Assertions.assertEquals("faridMousa@gmail.com", savedUser.getEmail().toString());

        // test the find user by id
        ID id = savedUser.getId();

        User foundUser = this.userRepository.find(id.toString());

        Assertions.assertNotNull(foundUser);
        Assertions.assertEquals("mousa", foundUser.getName());
        Assertions.assertEquals("faridMousa@gmail.com", foundUser.getEmail().toString());
    }
}
