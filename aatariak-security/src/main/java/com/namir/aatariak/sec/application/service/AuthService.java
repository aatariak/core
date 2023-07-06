package com.namir.aatariak.sec.application.service;

public interface AuthService {
    boolean validateCredentials(String username, String password);
}
