package com.modsen.authenticationservice.controllers.api;

import com.modsen.authenticationservice.core.dto.UserLoginDTO;
import com.modsen.authenticationservice.core.dto.UserRegisterDTO;
import com.modsen.authenticationservice.services.IAuthenticationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
@Tag(name = "AuthenticationController", description = "Provides operations for user authentication, validation of jwt-tokens")
public class AuthenticationApiController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final IAuthenticationService authenticationService;

    @Autowired
    public AuthenticationApiController(IAuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping("/register")
    @Operation(summary = "Register", description = "Allows user to register")
    public ResponseEntity<String> registerNewUser(@Valid @RequestBody UserRegisterDTO registerDTO) {
        logger.info("Registering new user with name={}", registerDTO.getUsername());

        authenticationService.saveUser(registerDTO);

        return ResponseEntity.status(HttpStatus.OK).body("user registered successfully");
    }

    @PostMapping("/login")
    @Operation(summary = "Login", description = "Allows user to login, take token")
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
    @Operation(summary = "Validate token", description = "Allows to validate jwt token, is using by others application microservices")
    public ResponseEntity<String> validateToken(@NotNull @NotEmpty @RequestParam("token") String token) {
        logger.info("Validating token={}", token);

        authenticationService.validateToken(token);

        return ResponseEntity.status(HttpStatus.OK).body("token valid");
    }
}
