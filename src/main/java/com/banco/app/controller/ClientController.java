package com.banco.app.controller;

import com.banco.app.exception.ClientNotFoundException;
import com.banco.app.model.Client;
import com.banco.app.service.ClientService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/v1/clients")
public class ClientController {

    private final ClientService clientService;

    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    @PostMapping
    public Mono<ResponseEntity<Client>> registerClient(@RequestBody Client client) {
        return clientService.registerClient(client);
    }

    @PutMapping("/{id}")
    public Mono<ResponseEntity<Client>> updateClient(@PathVariable Long id, @RequestBody Client client) {
        return clientService.updateClient(id, client)
                .switchIfEmpty(Mono.error(new ClientNotFoundException("Client not found")));
    }

    @DeleteMapping("/{id}")
    public Mono<ResponseEntity<Void>> deleteClient(@PathVariable Long id) {
        return clientService.deleteClient(id)
                .switchIfEmpty(Mono.error(new ClientNotFoundException("Client not found")));
    }


}
