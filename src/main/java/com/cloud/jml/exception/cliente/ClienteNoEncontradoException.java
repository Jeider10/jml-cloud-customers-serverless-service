package com.cloud.jml.exception.cliente;

import org.springframework.http.HttpStatus;

public class ClienteNoEncontradoException extends ClienteRuntimeException {

    public ClienteNoEncontradoException(Long identificacion) {
        super(
                HttpStatus.NOT_FOUND,
                "❌ [CONSULTA] Cliente no encontrado con identificación: " + identificacion);
    }
}
