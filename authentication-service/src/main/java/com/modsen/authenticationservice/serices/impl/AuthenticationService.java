package com.modsen.authenticationservice.serices.impl;

import com.modsen.authenticationservice.core.dto.UserLoginDTO;
import com.modsen.authenticationservice.core.dto.UserRegisterDTO;
import com.modsen.authenticationservice.core.mappers.UserMapper;
import com.modsen.authenticationservice.dao.repository.UserCredentialRepository;
import com.modsen.authenticationservice.entities.UserCredential;
import com.modsen.authenticationservice.serices.IAuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService implements IAuthenticationService {

    private final JwtService jwtService;

    private final UserCredentialRepository repository;

    private final PasswordEncoder passwordEncoder;

    private final UserMapper userMapper;

    private final AuthenticationManager authenticationManager;

    @Autowired
    public AuthenticationService(JwtService jwtService, UserCredentialRepository repository, PasswordEncoder passwordEncoder, UserMapper userMapper, AuthenticationManager authenticationManager) {
        this.jwtService = jwtService;
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
        this.userMapper = userMapper;
        this.authenticationManager = authenticationManager;
    }

    @Override
    public void saveUser(UserRegisterDTO registerDTO) {
        UserCredential credential = userMapper.registerDtoToCredential(registerDTO);
        credential.setPassword(passwordEncoder.encode(credential.getPassword()));

        repository.save(credential);
    }

    @Override
    public boolean authenticateUser(UserLoginDTO loginDTO) {
        Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginDTO.getUsername(), loginDTO.getPassword()));
        return authenticate.isAuthenticated();
    }

    @Override
    public String generateToken(String username) {
        return jwtService.generateToken(username);
    }

    @Override
    public void validateToken(String token) {
        jwtService.validateToken(token);
    }
}
