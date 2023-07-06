package com.namir.aatariak.sec.user.user.application.controller;

import org.springframework.stereotype.Controller;
import com.namir.aatariak.sec.user.user.domain.entity.User;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RequestMethod;
import com.namir.aatariak.shared.application.DTO.RegisterDto;
import org.springframework.web.bind.annotation.RequestMapping;
import com.namir.aatariak.sec.user.user.database.service.RegisterService;
import org.springframework.web.bind.MethodArgumentNotValidException;
import com.namir.aatariak.shared.application.Controller.BaseApiController;

import javax.validation.Valid;

@Controller
public class AuthController extends BaseApiController {

    private final RegisterService registerService;

    public AuthController(RegisterService registerService) {
        this.registerService = registerService;
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public @ResponseBody User register(@Valid @RequestBody RegisterDto user) throws MethodArgumentNotValidException {
        return registerService.register(user);
//        return ResponseEntity.ok("user is valid");
    }
}
