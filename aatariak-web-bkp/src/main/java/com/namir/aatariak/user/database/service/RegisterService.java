package com.namir.aatariak.sec.user.user.database.service;

import com.namir.aatariak.sec.user.user.domain.entity.User;
import com.namir.aatariak.shared.application.DTO.RegisterDto;
import com.namir.aatariak.sec.user.user.domain.valueObject.EmailAddress;
import org.springframework.web.bind.MethodArgumentNotValidException;

public interface RegisterService {
    public User register(RegisterDto userDto) throws MethodArgumentNotValidException;

    public Boolean isEmailUnique(EmailAddress emailAddress);
}
