package com.namir.aatariak.user.application.service;

import com.namir.aatariak.user.domain.entity.User;

import java.util.List;

public interface UserService {
    User loadByUsername(String username);
    User loadById(String id);
    List<User> loadAll();
    Long getUsersCount();
    User createUser(User user);
}
