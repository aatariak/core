package com.namir.aatariak.sec.context.application.service;

import com.namir.aatariak.sec.context.infrastructure.domainService.AatariakUserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserDataAccess extends UserDetailsService {

    @Override
    AatariakUserDetails loadUserByUsername(String username);
}
