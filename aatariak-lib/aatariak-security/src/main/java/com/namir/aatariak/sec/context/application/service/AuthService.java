package com.namir.aatariak.sec.context.application.service;

public interface AuthService {
    boolean validateCredentials(String username, String password);
}
