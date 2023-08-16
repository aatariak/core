package com.namir.aatariak.rest.controller;

import com.namir.aatariak.user.application.service.UserService;
import com.namir.aatariak.user.domain.entity.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("api/v1/users")
public class UserController {
    private final UserService userService;

    public UserController(
            UserService userService
    )
    {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<List<User>> users()
    {
        List<User> users = this.userService.loadAll();
        return ResponseEntity.status(HttpStatus.OK).body(users);
    }
}
