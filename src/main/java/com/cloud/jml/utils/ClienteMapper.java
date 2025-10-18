package com.cloud.jml.utils;

import com.cloud.jml.dto.ClienteRequestDTO;
import com.cloud.jml.dto.ClienteResponseDTO;
import com.cloud.jml.model.ClienteEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Slf4j
@Component // 🔹 Anotación para indicar que es un componente de Spring
public class ClienteMapper {

    private final ClienteFormatearFecha clienteFormatearFecha;

    public ClienteMapper(ClienteFormatearFecha clienteFormatearFecha) {
        this.clienteFormatearFecha = clienteFormatearFecha;
        log.info("🔥 ClienteMapper inicializado correctamente.");
    }

    public ClienteEntity mapRequestDtoToEntity(ClienteRequestDTO clienteRequestDTO) {
        log.info("📌 Iniciando mapeo DTO a Entity.");

        ClienteEntity clienteEntity = new ClienteEntity();

        clienteEntity.setIdentificacion(clienteRequestDTO.getIdentificacion());
        clienteEntity.setNombres(clienteRequestDTO.getNombres());
        clienteEntity.setApellidos(clienteRequestDTO.getApellidos());
        clienteEntity.setTelefono(clienteRequestDTO.getTelefono());
        clienteEntity.setDireccion(clienteRequestDTO.getDireccion());
        clienteEntity.setFechaCreacion(LocalDateTime.now());

        log.info("📌 Finalizando mapeo DTO a Entity.");

        return clienteEntity;
    }

    public ClienteResponseDTO mapEntityToResponseDto(ClienteEntity clienteEntity) {
        log.info("📌 Iniciando mapeo Entity a DTO.");

        ClienteResponseDTO clienteResponseDTO = new ClienteResponseDTO();

        clienteResponseDTO.setIdentificacion(clienteEntity.getIdentificacion());
        clienteResponseDTO.setNombres(clienteEntity.getNombres());
        clienteResponseDTO.setApellidos(clienteEntity.getApellidos());
        clienteResponseDTO.setTelefono(clienteEntity.getTelefono());
        clienteResponseDTO.setDireccion(clienteEntity.getDireccion());

        // 🔹 Formatear fechas
        clienteFormatearFecha.asignarFechasFormateadas(clienteEntity, clienteResponseDTO);

        log.info("📌 Finalizando mapeo Entity a DTO.");

        return clienteResponseDTO;
    }
}
