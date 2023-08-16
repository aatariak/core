package com.namir.aatariak.sec.context.infrastructure.repository;

import com.namir.aatariak.sec.context.domain.entity.User;
import com.namir.aatariak.shared.valueObjects.EmailAddress;

public interface SecUserRepository {

    User findByUsernameAndEnabledIsTrue(EmailAddress emailAddress);
}
