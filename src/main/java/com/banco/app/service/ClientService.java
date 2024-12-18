package com.banco.app.service;

import com.banco.app.model.Client;
import org.springframework.http.ResponseEntity;
import reactor.core.publisher.Mono;

public interface ClientService {

    Mono<ResponseEntity<Client>> registerClient(Client client);
    Mono<ResponseEntity<Client>> updateClient(Long id, Client client);
    Mono<ResponseEntity<Void>> deleteClient(Long id);
}
