package com.cloud.jml.exception;

public class ClienteNoEncontradoException extends RuntimeException {

    public ClienteNoEncontradoException(String identificacion) {
        super("No se encontró cliente con identificación: " + identificacion);
    }
}
