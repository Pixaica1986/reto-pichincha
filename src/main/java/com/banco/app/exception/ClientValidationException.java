package com.banco.app.exception;

public class ClientValidationException extends RuntimeException{
    public ClientValidationException(String message) {
        super(message);
    }
}
