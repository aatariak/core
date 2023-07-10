package com.namir.aatariak.sec.application.service;

import com.namir.aatariak.sec.domain.entity.Role;
import com.namir.aatariak.sec.domain.valueObject.Permission;
import jakarta.annotation.PostConstruct;
import org.springframework.data.domain.Page;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Pageable;
import com.namir.aatariak.sec.domain.entity.User;
import com.namir.aatariak.sec.user.rest.UserListOptions;
import com.namir.aatariak.shared.valueObjects.EmailAddress;
import com.namir.aatariak.sec.infrastructure.repository.UserRepository;
import com.namir.aatariak.sec.infrastructure.domainService.AatariakUserDetails;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

@Service
public class UserDetailsServiceImpl implements UserDataAccess {

    UserRepository userRepository;

    public UserDetailsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    private final int pwdLength = 9;
    private final List<Character> pwdCharPool = new ArrayList<>(
            Arrays.asList('a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z',
                    '@', '!', '%', '$',
                    'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z',
                    '_', '-', '#',
                    '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
                    '&', '^', '?'
            )
    );

    private String generatePassword() {
        SecureRandom random = new SecureRandom();
        byte[] bytes = new byte[pwdLength];
        random.nextBytes(bytes);

        StringBuilder password = new StringBuilder();
        for (int i = 0; i < bytes.length; i++) {
            int randomIndex = random.nextInt(pwdCharPool.size());
            password.append(pwdCharPool.get(randomIndex));
        }

        return password.toString();
    }

    @PostConstruct
    public void initAdminUser() {
        if (userRepository.count() == 0L) {
            String pwd = generatePassword();
            System.out.println("New root user was created with username 'namirabboud@gmail.com' and password '" + pwd + "'");
            User rootUser = new User();
            rootUser.setName("namir");
            rootUser.setEmail(new EmailAddress("namirabboud@gmail.com"));
            rootUser.setPassword(pwd);
            rootUser.setRoles(
                new HashSet<>(Arrays.asList(
                        new Role(Permission.createAdminPermission()),
                        new Role(Permission.createUserPermission()),
                        new Role(Permission.createAnyonePermission())
                ))
            );
            createUser(rootUser);
        }
    }

    @Override
    public AatariakUserDetails loadUserByUsername(String username) {
        User user =  this.userRepository.findByUsernameAndEnabledIsTrue(new EmailAddress(username));
        if (user == null) {
            throw new UsernameNotFoundException("User Not Found");
        }
        return new AatariakUserDetails(user);
    }

    @Override
    public AatariakUserDetails loadUserByApiKey(String apiKey) {
        return null;
    }

    @Override
    public Page<AatariakUserDetails> loadAllUsers(UserListOptions options, Pageable pageable) {

        return null;
    }

    @Override
    public User createUser(User user) {
        return userRepository.create(user);
    }

    @Override
    public User updateUser(User user) {
        return null;
    }

    @Override
    public List<User> getUsers() {
        return userRepository.getAllUsers();
    }

    @Override
    public Long getUsersCount() { return userRepository.count(); }

    @Override
    public User loadUserById(String id) {
        User user = this.userRepository.find(id);

        return user;
    }

    @Override
    public User loadUserByEmail(String email) {
        User user =  this.userRepository.findByUsernameAndEnabledIsTrue(new EmailAddress(email));

        return user;
    }

    @Override
    public void deleteUser(String username) {

    }

    @Override
    public void changePassword(String username, String oldPassword, String newPassword) {

    }

    @Override
    public void addApiKey(String username, String key) {

    }

    @Override
    public void removeApiKey(String username) {

    }

    @Override
    public boolean userExists(String username) {
        return false;
    }

}
