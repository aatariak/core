package com.namir.aatariak.aatariakauthserver.infrastructure.repository;

import com.namir.aatariak.aatariakauthserver.domain.entity.User;
import com.namir.aatariak.shared.valueObjects.EmailAddress;

import java.util.List;

public interface UserRepository {

    User find(String id);

    User create(User user);

    Long count();

    User findByUsernameAndEnabledIsTrue(EmailAddress emailAddress);

    List<User> getAllUsers();
}
