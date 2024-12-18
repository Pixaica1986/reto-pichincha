package com.banco.app.provider;

import com.banco.app.model.Client;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;

import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Mono;


import java.util.List;

@Repository
public class ClientValidationService {



    private final WebClient webClient;

    public ClientValidationService(WebClient webClient) {
        this.webClient = webClient;
    }

    public Mono<Boolean> checkIfClientExists(String name, String email) {
        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .queryParam("name", name)
                        .queryParam("email", email)
                        .build())
                .retrieve()
                .bodyToFlux(Client.class)
                .collectList()
                .map(clients -> clients != null && !clients.isEmpty());
    }


}
