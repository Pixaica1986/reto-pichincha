package com.banco.app.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import reactor.core.publisher.Mono;

@ControllerAdvice
public class GlobalExceptionHandler {


    // Manejar ClientNotFoundException
    @ExceptionHandler(ClientNotFoundException.class)
    public Mono<ResponseEntity<String>> handleClientNotFoundException(ClientNotFoundException ex) {
        return Mono.just(ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(ex.getMessage()));
    }

    // Manejar ClientValidationException
    @ExceptionHandler(ClientValidationException.class)
    public Mono<ResponseEntity<String>> handleClientValidationException(ClientValidationException ex) {
        return Mono.just(ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(ex.getMessage()));
    }

    // Manejar GenericException
    @ExceptionHandler(GenericException.class)
    public Mono<ResponseEntity<String>> handleGenericException(GenericException ex) {
        return Mono.just(ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ex.getMessage()));
    }

    // Manejar cualquier excepción no controlada
    @ExceptionHandler(Exception.class)
    public Mono<ResponseEntity<String>> handleException(Exception ex) {
        return Mono.just(ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("An unexpected error occurred: " + ex.getMessage()));
    }

}
