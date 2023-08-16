package com.namir.aatariak.user.application.service;

import com.namir.aatariak.shared.valueObjects.EmailAddress;
import com.namir.aatariak.user.domain.entity.Role;
import com.namir.aatariak.user.domain.entity.User;
import com.namir.aatariak.user.domain.valueObject.Permission;
import com.namir.aatariak.user.infrastructure.repository.UserRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

@Service
public class UserServiceImpl implements UserService{

    @Value("${aatariak.security.default-user-password}")
    private String defaultPassword;

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User loadByUsername(String username) {
        return this.userRepository.findByUsernameAndEnabledIsTrue(new EmailAddress(username));
    }

    @Override
    public User loadById(String id) {
        return this.userRepository.find(id);
    }

    @Override
    public List<User> loadAll() {
        return this.userRepository.getAllUsers();
    }

    @Override
    public Long getUsersCount() {
        return userRepository.count();
    }

    @Override
    public User createUser(User user) {
        return userRepository.create(user);
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
            String pwd = (this.defaultPassword != null && !this.defaultPassword.isEmpty()) ? this.defaultPassword : generatePassword();
            System.out.println("New root user was created with username 'namirabboud@gmail.com' and password '" + pwd + "'");
            User rootUser = new User();
            rootUser.setName("namir");
            rootUser.setEmail(new EmailAddress("namirabboud@gmail.com"));
            rootUser.setPassword(pwd);
            rootUser.setRoles(
                    new HashSet<>(Arrays.asList(
                            new Role(Permission.createAdminPermission()),
                            new Role(Permission.createPassengerPermission()),
                            new Role(Permission.createDriverPermission())
                    ))
            );
            createUser(rootUser);
        }
    }
}
