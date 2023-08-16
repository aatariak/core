package com.namir.aatariak.sec.context.application.service;

import org.springframework.stereotype.Service;
import com.namir.aatariak.sec.context.domain.entity.User;
import com.namir.aatariak.shared.valueObjects.EmailAddress;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import com.namir.aatariak.sec.context.infrastructure.repository.SecUserRepository;
import com.namir.aatariak.sec.context.infrastructure.domainService.AatariakUserDetails;

@Service
public class UserDetailsServiceImpl implements UserDataAccess {

    SecUserRepository userRepository;

    public UserDetailsServiceImpl(SecUserRepository userRepository) {
        this.userRepository = userRepository;
    }


    @Override
    public AatariakUserDetails loadUserByUsername(String username) {
        User user =  this.userRepository.findByUsernameAndEnabledIsTrue(new EmailAddress(username));
        if (user == null) {
            throw new UsernameNotFoundException("User Not Found");
        }
        return new AatariakUserDetails(user);
    }
}
