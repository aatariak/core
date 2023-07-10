package com.namir.aatariak.rest.controller.open;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import com.namir.aatariak.sec.domain.entity.User;
import org.springframework.web.bind.annotation.*;
import com.namir.aatariak.rest.request.AuthorizeRequest;
import com.namir.aatariak.sec.application.service.AuthService;
import com.namir.aatariak.sec.application.service.UserDataAccess;

import java.util.List;

@Slf4j
@RestController
@CrossOrigin(origins = "*")
@RequestMapping("api/v2/users")
public class OpenUserController {
    private UserDataAccess userService;

    private AuthService authService;

    public OpenUserController(
            UserDataAccess userService,
            AuthService authService
    )
    {
        this.userService = userService;
        this.authService = authService;
    }

    @GetMapping
    public ResponseEntity<List<User>> users()
    {
        List<User> users = this.userService.getUsers();
        return ResponseEntity.status(HttpStatus.OK).body(users);
    }

    @GetMapping("/count")
    public ResponseEntity<String> getUsersCount()
    {
        Long count = this.userService.getUsersCount();
        return ResponseEntity.status(HttpStatus.OK).body(count.toString());
    }

    @GetMapping("/{email}")
    public ResponseEntity<User> getUserByEmail(@PathVariable String email)
    {
        User user = this.userService.loadUserByEmail(email);
        return ResponseEntity.status(HttpStatus.OK).body(user);
    }

    @PostMapping("/authenticate")
    public ResponseEntity<String> authorize(@Valid @RequestBody AuthorizeRequest authorizeRequest)
    {
        if (!this.authService.validateCredentials(authorizeRequest.getEmail(), authorizeRequest.getPassword())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid username or password");
        }
        return ResponseEntity.status(HttpStatus.OK).body("Authentication successful");
    }
}
