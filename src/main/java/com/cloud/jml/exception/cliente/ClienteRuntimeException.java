package com.cloud.jml.exception.cliente;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class ClienteRuntimeException extends RuntimeException {

    private final HttpStatus status;

    public ClienteRuntimeException(HttpStatus status, String message) {
        super(message);
        this.status = status;
    }

    public ClienteRuntimeException(HttpStatus status, String message, Throwable cause) {
        super(message, cause);
        this.status = status;
    }
}
