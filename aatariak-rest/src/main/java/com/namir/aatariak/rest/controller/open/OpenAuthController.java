package com.namir.aatariak.rest.controller.open;

import com.namir.aatariak.rest.request.AuthorizeRequest;
import com.namir.aatariak.sec.context.application.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("api/v2/users")
public class OpenAuthController {

    private final AuthService authService;

    public OpenAuthController(
            AuthService authService
    ){
        this.authService = authService;
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
