package com.cloud.jml.exception;

import com.cloud.jml.exception.cliente.ClienteRuntimeException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // 👤 Errores de cliente
    @ExceptionHandler(ClienteRuntimeException.class)
    public ResponseEntity<Map<String, Object>> handleClienteErrors(ClienteRuntimeException ex) {
        return buildErrorResponse(
                ex.getStatus(),
                "👤 [CLIENTE] Error en cliente",
                ex.getMessage()
        );
    }

    // 🔥 Errores generales no controlados
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> handleGeneral(Exception ex) {
        return buildErrorResponse(
                HttpStatus.INTERNAL_SERVER_ERROR,
                "🔥 [GENERAL] Error interno del servidor",
                ex.getMessage()
        );
    }

    // 🧱 Método común de respuesta
    private ResponseEntity<Map<String, Object>> buildErrorResponse(HttpStatus status, String error, String message) {
        Map<String, Object> body = new HashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("status", status.value());
        body.put("error", error);
        body.put("message", message);

        return ResponseEntity.status(status).body(body);
    }
}
