package com.cloud.jml.exception.cliente;

import org.springframework.http.HttpStatus;

public class ClientePersistenceException extends ClienteRuntimeException {

    public ClientePersistenceException(String message) {
        super(HttpStatus.INTERNAL_SERVER_ERROR, "💾 [PERSISTENCIA] " + message);
    }

    public ClientePersistenceException(String message, Throwable cause) {
        super(
                HttpStatus.INTERNAL_SERVER_ERROR,
                "💾 [PERSISTENCIA] " + message +
                        (cause != null ? " | 💥 Causa: " + cause.getMessage() : "")
        );
    }

    // 🔒 Violación de integridad (constraint, duplicado, etc.) al guardar
    public static ClientePersistenceException integrityViolation(Throwable cause) {
        return new ClientePersistenceException(
                "❌ [INTEGRIDAD] Violación de integridad en base de datos al guardar el cliente",
                cause
        );
    }

    // ⚙️ Error técnico de acceso a datos
    public static ClientePersistenceException dataAccessError(Throwable cause) {
        return new ClientePersistenceException(
                "❌ [DATOS] Error de acceso a datos al intentar guardar el cliente",
                cause
        );
    }

    // 💥 Error inesperado
    public static ClientePersistenceException unexpected(Throwable cause) {
        return new ClientePersistenceException(
                "💥 [INESPERADO] Ocurrió un error inesperado al registrar el cliente",
                cause
        );
    }
}
