package com.cloud.jml.utils;

import com.cloud.jml.dto.ClienteResponseDTO;
import com.cloud.jml.model.ClienteEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

@Slf4j
@Component
public class ClienteFormatearFecha {

    private static final DateTimeFormatter FORMATTER =
            DateTimeFormatter.ofPattern("d/M/yyyy, h:mm:ss a", Locale.of("es", "CO"));

    public String formatearFecha(LocalDateTime fecha) {
        if (fecha == null) {
            return null;
        }

        String fechaFormateada = fecha.format(FORMATTER).toLowerCase();
        log.info("📌 Fecha formateada originalmente: {}", fechaFormateada);

        // Reemplazar y reasignar el valor "a. m." → "a.m." y "p. m." → "p.m."
        fechaFormateada = fechaFormateada
                .replace("a. m.", "a.m.")
                .replace("p. m.", "p.m.");

        log.info("📌 Fecha formateada final: {}", fechaFormateada);

        return fechaFormateada;
    }

    public void asignarFechasFormateadas(ClienteEntity clienteEntity, ClienteResponseDTO clienteResponseDTO) {
        log.info("📌 Iniciando asignación de fechas formateadas.");

        if (clienteEntity.getFechaCreacion() != null) {
            String fechaCreacion = formatearFecha(clienteEntity.getFechaCreacion());
            log.info("📌 Fecha creación formateada: {}", fechaCreacion);

            clienteResponseDTO.setFechaCreacion(fechaCreacion);
        } else {
            clienteResponseDTO.setFechaCreacion(null);
        }

        if (clienteEntity.getFechaActualizacion() != null) {
            String fechaActualizacion = formatearFecha(clienteEntity.getFechaActualizacion());
            log.info("📌 Fecha actualización formateada: {}", fechaActualizacion);

            clienteResponseDTO.setFechaActualizacion(fechaActualizacion);
        } else {
            clienteResponseDTO.setFechaActualizacion(null);
        }
    }
}
