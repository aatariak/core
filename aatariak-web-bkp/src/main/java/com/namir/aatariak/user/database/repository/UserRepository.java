package com.namir.aatariak.sec.user.user.database.repository;

import com.namir.aatariak.sec.user.user.domain.entity.User;
import com.namir.aatariak.sec.user.user.domain.valueObject.EmailAddress;

public interface UserRepository {
    User createUser(User user);

    User getUser(String id);

    User getUserByEmail(EmailAddress emailAddress);

    Long count();
}
