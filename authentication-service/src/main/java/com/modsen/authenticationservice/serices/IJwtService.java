package com.modsen.authenticationservice.serices;

public interface IJwtService {
    void validateToken(String token);

    String generateToken(String userName);
}
