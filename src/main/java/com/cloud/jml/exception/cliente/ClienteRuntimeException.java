package com.cloud.jml.exception.cliente;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public abstract class ClienteRuntimeException extends RuntimeException {

    private final HttpStatus status;

    protected ClienteRuntimeException(HttpStatus status, String message) {
        super(message);
        this.status = status;
    }

    protected ClienteRuntimeException(HttpStatus status, String message, Throwable cause) {
        super(message, cause);
        this.status = status;
    }
}
