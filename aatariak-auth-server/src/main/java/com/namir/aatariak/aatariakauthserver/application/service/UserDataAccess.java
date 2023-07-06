package com.namir.aatariak.aatariakauthserver.application.service;

import com.namir.aatariak.aatariakauthserver.domain.entity.User;
import com.namir.aatariak.aatariakauthserver.infrastructure.domainService.AatariakUserDetails;
import com.namir.aatariak.sec.user.rest.UserListOptions;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserDataAccess extends UserDetailsService {

    @Override
    AatariakUserDetails loadUserByUsername(String username);

    AatariakUserDetails loadUserByApiKey(String apiKey);

    Page<AatariakUserDetails> loadAllUsers(UserListOptions options, Pageable pageable);

    User createUser(User user);

    User updateUser(User user);

    List<User> getUsers();

    void deleteUser(String username);

    void changePassword(String username, String oldPassword, String newPassword);

    void addApiKey(String username, String key);

    void removeApiKey(String username);

    boolean userExists(String username);
}
