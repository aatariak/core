package com.namir.aatariak.user.infrastructure.repository;

import com.namir.aatariak.shared.valueObjects.EmailAddress;
import com.namir.aatariak.user.domain.entity.User;
import org.springframework.lang.NonNull;

import java.util.List;

public interface UserRepository {

    User find(@NonNull String id);

    User create(User user);

    Long count();

    User findByUsernameAndEnabledIsTrue(EmailAddress emailAddress);

    List<User> getAllUsers();
}
