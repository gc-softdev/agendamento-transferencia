package com.backend.agendamentotransferencia.error;

import org.springframework.http.HttpStatus;

public class TransferenciaException extends RuntimeException {
    private final HttpStatus status;

    public TransferenciaException(String message, HttpStatus status) {
        super(message);
        this.status = status;
    }

    public HttpStatus getStatus() {
        return status;
    }
}