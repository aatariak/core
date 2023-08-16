package com.namir.aatariak.rest.controller.open;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.namir.aatariak.user.domain.entity.User;
import com.namir.aatariak.user.application.service.UserService;

import java.util.List;

@Slf4j
@RestController
@CrossOrigin(origins = "*")
@RequestMapping("api/v2/users")
public class OpenUserController {
    private final UserService userService;

    public OpenUserController(
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

    @GetMapping("/count")
    public ResponseEntity<String> getUsersCount()
    {
        Long count = this.userService.getUsersCount();
        return ResponseEntity.status(HttpStatus.OK).body(count.toString());
    }

    @GetMapping("/{email}")
    public ResponseEntity<User> getUserByEmail(@PathVariable String email)
    {
        User user = this.userService.loadByUsername(email);
        return ResponseEntity.status(HttpStatus.OK).body(user);
    }
}
