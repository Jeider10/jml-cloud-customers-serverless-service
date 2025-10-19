package com.cloud.jml.exception.cliente;

import org.springframework.http.HttpStatus;

public class ClienteDeletionException extends ClienteRuntimeException {

    public ClienteDeletionException(String message) {
        super(HttpStatus.INTERNAL_SERVER_ERROR, "🗑️ [ELIMINACIÓN] " + message);
    }

    public ClienteDeletionException(String message, Throwable cause) {
        super(
                HttpStatus.INTERNAL_SERVER_ERROR,
                "🗑️ [ELIMINACIÓN] " + message +
                        (cause != null ? " | 💥 Causa: " + cause.getMessage() : "")
        );
    }

    // 🔒 Violación de integridad referencial (por constraints o dependencias)
    public static ClienteDeletionException integrityViolation(Throwable cause) {
        return new ClienteDeletionException(
                "❌ [INTEGRIDAD] No se pudo eliminar el cliente debido a una violación de integridad referencial",
                cause
        );
    }

    // ⚙️ Error de acceso a datos
    public static ClienteDeletionException dataAccessError(Throwable cause) {
        return new ClienteDeletionException(
                "❌ [DATOS] Error de acceso a la base de datos al intentar eliminar el cliente",
                cause
        );
    }

    // 💥 Error inesperado
    public static ClienteDeletionException unexpected(Throwable cause) {
        return new ClienteDeletionException(
                "💥 [INESPERADO] Ocurrió un error inesperado al intentar eliminar el cliente",
                cause
        );
    }
}
