package com.namir.aatariak.sec.user.user.application.service;

import com.namir.aatariak.sec.user.user.domain.entity.User;
import com.namir.aatariak.sec.user.user.infrastructure.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("userService")
public class UserServiceImpl implements UserService {

    protected UserRepository userRepository;

    public UserServiceImpl(
            UserRepository userRepository
    )
    {
        this.userRepository = userRepository;
    }

    @Override
    public List<User> getAll() {
        List<User> users = this.userRepository.getAll();
        return users;
    }
}
