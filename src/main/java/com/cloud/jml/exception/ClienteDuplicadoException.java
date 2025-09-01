package com.cloud.jml.exception;

public class ClienteDuplicadoException extends RuntimeException {

    public ClienteDuplicadoException(String identificacion) {
        super("El cliente con identificación " + identificacion + " ya existe.");
    }
}
