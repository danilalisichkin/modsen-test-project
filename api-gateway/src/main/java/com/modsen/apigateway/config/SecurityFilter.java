package com.modsen.apigateway.config;

import com.modsen.apigateway.controllers.clients.AuthenticationClient;
import com.modsen.apigateway.exceptions.ExceptionMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
public class SecurityFilter implements GlobalFilter {
    private final RouteValidator validator;

    private final AuthenticationClient authenticationClient;

    @Autowired
    public SecurityFilter(RouteValidator validator, @Lazy AuthenticationClient authenticationClient) {
        this.validator = validator;
        this.authenticationClient = authenticationClient;
    }

    @Override
    public Mono<Void> filter(
            ServerWebExchange exchange,
            GatewayFilterChain chain) {
        if (validator.isSecured.test(exchange.getRequest())) {

            if (!exchange.getRequest().getHeaders().containsKey(HttpHeaders.AUTHORIZATION)) {
                throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "missing authorization header - authorization required");
            }

            String authHeader = exchange.getRequest().getHeaders().get(HttpHeaders.AUTHORIZATION).get(0);
            if (authHeader != null && authHeader.startsWith("Bearer ")) {
                authHeader = authHeader.substring(7);
            }

            ResponseEntity<Object> response = authenticationClient.validateToken(authHeader);
            if (!response.getBody().equals("token valid") || !response.getStatusCode().equals(HttpStatus.OK)) {
                ExceptionMessage responseMessage = (ExceptionMessage) response.getBody();
                throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, responseMessage.getMessage() + " - " + responseMessage.getCause());
            }
        }
        return chain.filter(exchange);
    }
}