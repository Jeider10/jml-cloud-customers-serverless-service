package com.cloud.jml.exception.cliente;

import org.springframework.http.HttpStatus;

public class ClienteNoEncontradoException extends ClienteRuntimeException {

    public ClienteNoEncontradoException(Long identificacion) {
        super(HttpStatus.NOT_FOUND, "❌ Cliente no encontrado con identificación: " + identificacion);
    }
}
