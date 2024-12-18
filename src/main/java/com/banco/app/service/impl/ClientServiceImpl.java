package com.banco.app.service.impl;

import com.banco.app.exception.ClientNotFoundException;
import com.banco.app.model.Client;
import com.banco.app.provider.ClientValidationService;
import com.banco.app.repository.ClientRepository;
import com.banco.app.service.ClientService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.util.Optional;

@Service
public class ClientServiceImpl implements ClientService {

    private final ClientRepository clientRepository;
    private final ClientValidationService clientValidationService;

    public ClientServiceImpl(ClientRepository clientRepository, ClientValidationService clientValidationService) {
        this.clientRepository = clientRepository;
        this.clientValidationService = clientValidationService;
    }

    @Override
    public Mono<ResponseEntity<Client>> registerClient(Client client) {
        return clientValidationService
                .checkIfClientExists(client.getName(), client.getEmail()) // Retorna Mono<Boolean>
                .flatMap(exists -> {
                    if (exists) {
                        client.setStatus("exists");
                    } else {
                        client.setStatus("active");
                    }
                    return Mono.fromCallable(() -> clientRepository.save(client));
                })
                .map(savedClient -> ResponseEntity.ok(savedClient))
                .subscribeOn(Schedulers.boundedElastic());
    }

    @Override
    public Mono<ResponseEntity<Client>> updateClient(Long id, Client client) {
        return Mono.fromCallable(() -> clientRepository.findById(id))
                .flatMap(optionalClient -> optionalClient
                        .map(existingClient -> {
                            existingClient.setName(client.getName());
                            existingClient.setEmail(client.getEmail());
                            return Mono.fromCallable(() -> clientRepository.save(existingClient));
                        })
                        .orElseGet(() -> Mono.error(new ClientNotFoundException("Client with ID " + id + " not found"))))
                .map(updatedClient -> ResponseEntity.ok(updatedClient))
                .subscribeOn(Schedulers.boundedElastic());
    }

    @Override
    public Mono<ResponseEntity<Void>> deleteClient(Long id) {
        return Mono.fromRunnable(() -> clientRepository.deleteById(id))
                .then(Mono.just(ResponseEntity.noContent().<Void>build()))
                .switchIfEmpty(Mono.error(new ClientNotFoundException("Client with ID " + id + " not found"))) // Si el cliente no existe, lanzamos la excepción
                .subscribeOn(Schedulers.boundedElastic());
    }
}
