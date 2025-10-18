package com.cloud.jml.exception.cliente;

import org.springframework.http.HttpStatus;

public class ClienteDeletionException extends ClienteRuntimeException {

    public ClienteDeletionException(String message) {
        super(HttpStatus.INTERNAL_SERVER_ERROR, "🗑️ " + message);
    }

    public ClienteDeletionException(String message, Throwable cause) {
        super(HttpStatus.INTERNAL_SERVER_ERROR, "🗑️ " + message + " | Causa: " + cause.getMessage());
    }

    // 🔒 Error por violación de integridad (constraint, duplicado, etc.) al eliminar
    public static ClienteDeletionException integrityViolation(Throwable cause) {
        return new ClienteDeletionException("❌ No se pudo eliminar el cliente debido a una violación de integridad referencial", cause);
    }

    // ⚙️ Error al acceder o comunicarse con la base de datos al eliminar
    public static ClienteDeletionException dataAccessError(Throwable cause) {
        return new ClienteDeletionException("❌ Error de acceso a datos al intentar eliminar el cliente", cause);
    }

    // 💥 Error inesperado (no contemplado en los anteriores) al eliminar
    public static ClienteDeletionException unexpected(Throwable cause) {
        return new ClienteDeletionException("❌ Error inesperado al intentar eliminar el cliente", cause);
    }
}
