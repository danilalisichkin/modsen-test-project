package com.modsen.authenticationservice.controllers.api;

import com.modsen.authenticationservice.core.dto.UserLoginDTO;
import com.modsen.authenticationservice.core.dto.UserRegisterDTO;
import com.modsen.authenticationservice.serices.impl.AuthenticationService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/authentication")
public class AuthenticationApiController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final AuthenticationService authenticationService;

    public AuthenticationApiController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping("/register")
    public ResponseEntity<String> addNewUser(@Valid @RequestBody UserRegisterDTO registerDTO) {
        logger.info("Registering new user with name={}", registerDTO.getUsername());

        authenticationService.saveUser(registerDTO);

        return ResponseEntity.status(HttpStatus.OK).body("user registered successfully");
    }

    @PostMapping("/login")
    public ResponseEntity<String> getToken(@Valid @RequestBody UserLoginDTO loginDTO) {
        logger.info("Authenticating user with name={}", loginDTO.getUsername());

        if (authenticationService.authenticateUser(loginDTO)) {
            String token = authenticationService.generateToken(loginDTO.getUsername());
            return ResponseEntity.status(HttpStatus.OK).body(token);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    @GetMapping("/validate")
    public ResponseEntity<String> validateToken(@NotNull @NotEmpty @RequestParam("token") String token) {
        logger.info("Validating token={}", token);

        authenticationService.validateToken(token);

        return ResponseEntity.status(HttpStatus.OK).body("token valid");
    }
}
