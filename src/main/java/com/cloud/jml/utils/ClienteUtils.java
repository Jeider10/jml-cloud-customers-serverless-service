package com.cloud.jml.utils;

import com.cloud.jml.dto.ClienteRequestDTO;
import com.cloud.jml.dto.ClienteResponseDTO;
import com.cloud.jml.exception.ClienteNoEncontradoException;
import com.cloud.jml.model.ClienteEntity;
import com.cloud.jml.repository.ClienteRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Optional;

@Slf4j
@Component // 🔹 Anotación para indicar que es un componente de Spring
public class ClienteUtils {

    private static final DateTimeFormatter FORMATTER =
            DateTimeFormatter.ofPattern("d/M/yyyy, h:mm:ss a", Locale.of("es", "CO"));

    private final ClienteRepository clienteRepository;

    public ClienteUtils(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
        log.info("🔥 ClienteUtils inicializado correctamente.");
    }

    public ClienteEntity validarExistenciaCliente(ClienteRequestDTO clienteRequestDTO) {
        Optional<ClienteEntity> optionalProveedor = clienteRepository.findByIdentificacion(clienteRequestDTO.getIdentificacion());

        if (optionalProveedor.isPresent()) {
            log.info("📌 Cliente encontrado con Identificación: {}", clienteRequestDTO.getIdentificacion());
            return optionalProveedor.get();
        } else {
            log.warn("⚠️ Cliente no encontrado con Identificación: {}", clienteRequestDTO.getIdentificacion());
            throw new ClienteNoEncontradoException(clienteRequestDTO.getIdentificacion());
        }
    }

    public void actualizarDatosCliente(ClienteRequestDTO clienteRequestDTO, ClienteEntity proveedorEntity) {
        // Actualizamos solo los campos permitidos
        proveedorEntity.setIdentificacion(clienteRequestDTO.getIdentificacion());
        proveedorEntity.setNombres(clienteRequestDTO.getNombres());
        proveedorEntity.setApellidos(clienteRequestDTO.getApellidos());
        proveedorEntity.setTelefono(clienteRequestDTO.getTelefono());
        proveedorEntity.setDireccion(clienteRequestDTO.getDireccion());

        // Actualizamos la fecha de actualización
        proveedorEntity.setFechaActualizacion(LocalDateTime.now());
    }

    public String formatearFecha(LocalDateTime fecha) {
        String fechaFormateada = fecha.format(FORMATTER).toLowerCase();
        log.info("📌 Fecha formateada originalmente: {}", fechaFormateada);

        // Reemplazar y reasignar el valor "a. m." → "a.m." y "p. m." → "p.m."
        fechaFormateada = fechaFormateada
                .replace("a. m.", "a.m.")
                .replace("p. m.", "p.m.");

        log.info("📌 Fecha formateada final: {}", fechaFormateada);

        return fechaFormateada;
    }

    public void asignarFechasFormateadas(ClienteEntity proveedorEntity, ClienteResponseDTO clienteResponseDTO) {
        if (proveedorEntity.getFechaCreacion() != null) {
            String fechaCreacion = formatearFecha(proveedorEntity.getFechaCreacion());
            log.info("📌 Fecha creación formateada: {}", fechaCreacion);

            clienteResponseDTO.setFechaCreacion(fechaCreacion);
        } else {
            clienteResponseDTO.setFechaCreacion(null);
        }

        if (proveedorEntity.getFechaActualizacion() != null) {
            String fechaActualizacion = formatearFecha(proveedorEntity.getFechaActualizacion());
            log.info("📌 Fecha actualización formateada: {}", fechaActualizacion);

            clienteResponseDTO.setFechaActualizacion(fechaActualizacion);
        } else {
            clienteResponseDTO.setFechaActualizacion(null);
        }
    }
}
