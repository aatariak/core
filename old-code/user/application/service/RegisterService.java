package com.namir.aatariak.sec.user.user.application.service;

import com.namir.aatariak.sec.user.user.domain.entity.User;
import com.namir.aatariak.shared.valueObjects.EmailAddress;
import com.namir.aatariak.sec.user.user.application.dto.RegisterDto;

public interface RegisterService {
    public User register(RegisterDto userDto) throws Exception;

    public Boolean isEmailUnique(EmailAddress emailAddress);
}
