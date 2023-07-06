package com.namir.aatariak.sec.user.user.infrastructure.repository;

import com.namir.aatariak.sec.user.user.domain.entity.User;
import com.namir.aatariak.shared.valueObjects.EmailAddress;

import java.util.List;

public interface UserRepository {
    User createUser(User user);

    User getUser(String id);

    User getUserByEmail(EmailAddress emailAddress);

    Long count();

    List<User> getAll();
}
