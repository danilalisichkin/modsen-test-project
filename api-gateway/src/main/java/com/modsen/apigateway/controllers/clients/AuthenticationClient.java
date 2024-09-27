package com.modsen.apigateway.controllers.clients;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class AuthenticationClient {
    private final WebClient webClient;

    @Autowired
    public AuthenticationClient(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.build();
    }

    public Mono<String> validateToken(String token) {
        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/validate")
                        .queryParam("token", token)
                        .build())
                .retrieve()
                .bodyToMono(String.class);
    }
}
