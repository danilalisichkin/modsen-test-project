package com.modsen.authenticationservice.serices;

import com.modsen.authenticationservice.core.dto.UserLoginDTO;
import com.modsen.authenticationservice.core.dto.UserRegisterDTO;

public interface IAuthenticationService {
    void saveUser(UserRegisterDTO registerDTO);

    boolean authenticateUser(UserLoginDTO loginDTO);

    String generateToken(String username);

    void validateToken(String token);
}
