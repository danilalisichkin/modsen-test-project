package com.modsen.authenticationservice.services;

public interface IJwtService {
    void validateToken(String token);

    String generateToken(String userName);
}
