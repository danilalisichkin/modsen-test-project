package com.modsen.authenticationservice.serices.impl;

import com.modsen.authenticationservice.core.dto.UserLoginDTO;
import com.modsen.authenticationservice.core.dto.UserRegisterDTO;
import com.modsen.authenticationservice.core.mappers.UserMapper;
import com.modsen.authenticationservice.dao.repository.UserCredentialRepository;
import com.modsen.authenticationservice.entities.UserCredential;
import com.modsen.authenticationservice.exceptions.DataUniquenessConflictException;
import com.modsen.authenticationservice.serices.IAuthenticationService;
import com.modsen.authenticationservice.serices.IJwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService implements IAuthenticationService {

    private final IJwtService jwtService;

    private final UserCredentialRepository credentialRepository;

    private final PasswordEncoder passwordEncoder;

    private final UserMapper userMapper;

    private final AuthenticationManager authenticationManager;

    @Autowired
    public AuthenticationService(IJwtService jwtService, UserCredentialRepository credentialRepository, PasswordEncoder passwordEncoder, UserMapper userMapper, AuthenticationManager authenticationManager) {
        this.jwtService = jwtService;
        this.credentialRepository = credentialRepository;
        this.passwordEncoder = passwordEncoder;
        this.userMapper = userMapper;
        this.authenticationManager = authenticationManager;
    }

    @Override
    public void saveUser(UserRegisterDTO registerDTO) {
        UserCredential credential = userMapper.registerDtoToCredential(registerDTO);

        if (credentialRepository.findByUsername(registerDTO.getUsername()).isPresent()) {
            throw new DataUniquenessConflictException();
        }

        credential.setPassword(passwordEncoder.encode(credential.getPassword()));

        credentialRepository.save(credential);
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
