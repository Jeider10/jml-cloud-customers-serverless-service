package com.cloud.jml.exception.cliente;

import org.springframework.http.HttpStatus;

public class ClientePersistenceException extends ClienteRuntimeException {

    public ClientePersistenceException(String message) {
        super(HttpStatus.INTERNAL_SERVER_ERROR, "💾 " + message);
    }

    public ClientePersistenceException(String message, Throwable cause) {
        super(HttpStatus.INTERNAL_SERVER_ERROR, "💾 " + message + " | Causa: " + cause.getMessage());
    }

    // 🔒 Error por violación de integridad (constraint, duplicado, etc.) al guardar
    public static ClientePersistenceException integrityViolation(Throwable cause) {
        return new ClientePersistenceException("❌ Violación de integridad en base de datos al guardar el cliente", cause);
    }

    // ⚙️ Error al acceder o comunicarse con la base de datos al guardar
    public static ClientePersistenceException dataAccessError(Throwable cause) {
        return new ClientePersistenceException("❌ Error de acceso a datos al intentar guardar el cliente", cause);
    }

    // 💥 Error inesperado (no contemplado en los anteriores) al guardar
    public static ClientePersistenceException unexpected(Throwable cause) {
        return new ClientePersistenceException("❌ Error inesperado al registrar el cliente", cause);
    }
}
