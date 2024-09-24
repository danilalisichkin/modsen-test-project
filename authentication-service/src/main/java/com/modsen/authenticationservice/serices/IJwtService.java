package com.modsen.authenticationservice.serices;

import java.util.Map;

public interface IJwtService {
    void validateToken(String token);

    String generateToken(String userName);

    String createToken(Map<String, Object> claims, String userName);

    String getSignKey();
}
