package com.modsen.apigateway.config;

import com.modsen.apigateway.controllers.clients.AuthenticationClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

@Component
public class SecurityFilter extends AbstractGatewayFilterFactory<SecurityFilter.Config> {
    private final RouteValidator validator;

    private final AuthenticationClient authenticationClient;

    @Autowired
    public SecurityFilter(RouteValidator validator, @Lazy AuthenticationClient authenticationClient) {
        super(Config.class);
        this.validator = validator;
        this.authenticationClient = authenticationClient;
    }

    @Override
    public GatewayFilter apply(Config config) {
        return ((exchange, chain) -> {
            if (validator.isSecured.test((ServerHttpRequest) exchange.getRequest())) {

                if (!exchange.getRequest().getHeaders().containsKey(HttpHeaders.AUTHORIZATION)) {
                    throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "missing authorization header");
                }

                String authHeader = exchange.getRequest().getHeaders().get(HttpHeaders.AUTHORIZATION).get(0);
                if (authHeader != null && authHeader.startsWith("Bearer ")) {
                    authHeader = authHeader.substring(7);
                }

                ResponseEntity<String> response = authenticationClient.validateToken(authHeader);
                if (!response.getBody().equals("token valid") || !response.getStatusCode().equals(HttpStatus.OK)) {
                    throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "un authorized access to application");
                }
            }
            return chain.filter(exchange);
        });
    }

    public static class Config {

    }
}