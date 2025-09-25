package com.cloud.jml.exception;

public class ClienteDuplicadoException extends RuntimeException {

    public ClienteDuplicadoException(Long identificacion) {
        super("El cliente con identificación " + identificacion + " ya existe.");
    }
}
