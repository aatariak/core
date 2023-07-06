package com.namir.aatariak.rest.controller;

import com.namir.aatariak.sec.application.service.UserDataAccess;
import com.namir.aatariak.sec.domain.entity.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("api/v1/users") // this short mapping guarantees it cannot be a short code (min five characters)
public class UserController {
    private UserDataAccess userService;

    public UserController(
            UserDataAccess userService
    )
    {
        this.userService = userService;
    }

//    @GetMapping
//    public ResponseEntity<RestResult<User>> users()
//    {
//        List<User> users = this.userService.getAll();
//        return users;
//        return ResponseEntity.status(HttpStatus.OK).body(new RestResult<User>(
//                users,
//
//        ))
//    }
    @GetMapping
    public ResponseEntity<List<User>> users()
    {
        List<User> users = this.userService.getUsers();
        return ResponseEntity.status(HttpStatus.OK).body(users);
    }
}
