package com.namir.aatariak.sec.user.user.database.service;

import org.springframework.stereotype.Service;
import org.springframework.validation.FieldError;
import com.namir.aatariak.sec.user.user.domain.entity.User;
import org.springframework.validation.BindingResult;
import org.springframework.validation.MapBindingResult;
import com.namir.aatariak.shared.application.DTO.RegisterDto;
import com.namir.aatariak.sec.user.user.domain.valueObject.EmailAddress;
import com.namir.aatariak.sec.user.user.database.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.MethodArgumentNotValidException;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Random;

import static com.namir.aatariak.sec.user.user.domain.entity.UserAuthority.USER_ROLE;

@Service("registerService")
public class RegisterServiceImpl implements RegisterService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    public RegisterServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User register(RegisterDto userDto) throws MethodArgumentNotValidException {
        if (!this.isEmailUnique(userDto.getEmail())) {
            BindingResult bindingResult = new MapBindingResult(new HashMap<>(), "errorBag");
            bindingResult.addError(new FieldError("errorBag", "email", "Email Not Unique"));

            throw new MethodArgumentNotValidException(null, bindingResult);
        }

        User user = new User();
        user.setName(userDto.getName());
        user.setEmail(userDto.getEmail());
        user.setPassword(this.passwordEncoder.encode(userDto.getPassword()));

        // for now give him a default role
        user.addAuthority(USER_ROLE);

        return userRepository.createUser(user);
    }

    public Boolean isEmailUnique(EmailAddress emailAddress) {
        User alreadyExists = userRepository.getUserByEmail(emailAddress);
        if (alreadyExists != null) {
            return false;
        }
        return true;
    }

    @PostConstruct
    public void initUser() throws MethodArgumentNotValidException {
        if (this.userRepository.count() == 0L) {
            String password = this.generatePassword();
            System.out.println("default@test.com user has been created with default password: " + password);
            RegisterDto registerDto = new RegisterDto();
            registerDto.setEmail("default@test.com");
            registerDto.setPassword(password);
            registerDto.setName("Default User");

            this.register(registerDto);
        }
    }

    private String generatePassword() {
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!@#$%^&*()_+";
        int length = 5;

        StringBuilder password = new StringBuilder();

        Random random = new Random();
        for (int i = 0; i < length; i++) {
            password.append(characters.charAt(random.nextInt(characters.length())));
        }

        return password.toString();
    }
}
