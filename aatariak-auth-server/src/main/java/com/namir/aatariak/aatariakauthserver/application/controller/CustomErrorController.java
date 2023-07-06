package com.namir.aatariak.aatariakauthserver.application.controller;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;

@ControllerAdvice
public class CustomErrorController {
    @ExceptionHandler(Exception.class)
    public String handleError() {
        // Handle the error and return an appropriate view or redirect
        // For example, you can return a custom error page
        return "error";
    }
}
