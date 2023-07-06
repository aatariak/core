package com.namir.aatariak.sec.infrastructure.repository;

import com.namir.aatariak.sec.domain.entity.User;
import com.namir.aatariak.shared.valueObjects.EmailAddress;
import org.springframework.lang.NonNull;

import java.util.List;

public interface UserRepository {

    User find(@NonNull String id);

    User create(User user);

    Long count();

    User findByUsernameAndEnabledIsTrue(EmailAddress emailAddress);

    List<User> getAllUsers();
}
