package com.modsen.apigateway.controllers.clients;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "authentication-service", path = "/api/v1/authentication")
public interface AuthenticationClient {

    @GetMapping("/validate")
    ResponseEntity<String> validateToken(@NotNull @NotEmpty @RequestParam("token") String token);
}
