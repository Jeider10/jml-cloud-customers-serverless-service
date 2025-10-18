package com.cloud.jml.exception.cliente;

import org.springframework.http.HttpStatus;

public class ClienteDuplicadoException extends ClienteRuntimeException {

    public ClienteDuplicadoException(Long identificacion) {
        super(HttpStatus.CONFLICT, "⚠️ Cliente duplicado con identificación " + identificacion);
    }
}
