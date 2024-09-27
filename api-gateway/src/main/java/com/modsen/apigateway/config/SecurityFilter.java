package com.modsen.apigateway.config;

import com.modsen.apigateway.controllers.clients.AuthenticationClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
public class SecurityFilter implements GlobalFilter, Ordered {
    private final RouteValidator validator;
    private final AuthenticationClient authenticationClient;

    @Autowired
    public SecurityFilter(RouteValidator validator, AuthenticationClient authenticationClient) {
        this.validator = validator;
        this.authenticationClient = authenticationClient;
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        if (validator.isSecured.test(exchange.getRequest())) {
            if (!exchange.getRequest().getHeaders().containsKey(HttpHeaders.AUTHORIZATION)) {
                return Mono.error(new ResponseStatusException(HttpStatus.UNAUTHORIZED, "missing authorization header - authorization required"));
            }

            String authHeader = exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
            if (authHeader != null && authHeader.startsWith("Bearer ")) {
                authHeader = authHeader.substring(7);
            }

            return authenticationClient.validateToken(authHeader)
                    .flatMap(response -> {
                        if ("token valid".equals(response)) {
                            return chain.filter(exchange);
                        } else {
                            return Mono.error(new ResponseStatusException(HttpStatus.UNAUTHORIZED, "invalid token - " + response));
                        }
                    })
                    .onErrorResume(e -> {
                        if (e instanceof WebClientResponseException) {
                            return Mono.error(e);
                        } else {
                            return Mono.error(new ResponseStatusException(HttpStatus.UNAUTHORIZED, "un authorized access to application - authorization required", e));
                        }
                    });
        }
        return chain.filter(exchange);
    }

    @Override
    public int getOrder() {
        return -1;
    }
}