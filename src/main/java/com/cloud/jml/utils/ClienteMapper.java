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

    private final ClienteUtils clienteUtils;

    public ClienteMapper(ClienteUtils clienteUtils) {
        this.clienteUtils = clienteUtils;
    }

    // ------------------ 🔹 Métodos de Mapeos ------------------

    public ClienteEntity mapRequestDtoToEntity(ClienteRequestDTO clienteRequestDTO) {
        log.info("📌 Iniciando mapeo DTO a Entity para crear Cliente");

        ClienteEntity proveedorEntity = new ClienteEntity();

        proveedorEntity.setIdentificacion(clienteRequestDTO.getIdentificacion());
        proveedorEntity.setNombres(clienteRequestDTO.getNombres());
        proveedorEntity.setApellidos(clienteRequestDTO.getApellidos());
        proveedorEntity.setTelefono(clienteRequestDTO.getTelefono());
        proveedorEntity.setDireccion(clienteRequestDTO.getDireccion());
        proveedorEntity.setFechaCreacion(LocalDateTime.now());

        log.info("📌 Finalizando mapeo DTO a Entity para crear Cliente");

        return proveedorEntity;
    }

    public ClienteResponseDTO mapEntityToResponseDto(ClienteEntity proveedorEntity) {
        log.info("📌 Iniciando mapeo Entity a DTO para crear Cliente");

        ClienteResponseDTO proveedorResponseDTO = new ClienteResponseDTO();

        proveedorResponseDTO.setIdentificacion(proveedorEntity.getIdentificacion());
        proveedorResponseDTO.setNombres(proveedorEntity.getNombres());
        proveedorResponseDTO.setApellidos(proveedorEntity.getApellidos());
        proveedorResponseDTO.setTelefono(proveedorEntity.getTelefono());
        proveedorResponseDTO.setDireccion(proveedorEntity.getDireccion());

        // 🔹 Formatear fechas
        clienteUtils.asignarFechasFormateadas(proveedorEntity, proveedorResponseDTO);

        log.info("📌 Finalizando mapeo Entity a DTO para crear Cliente");

        return proveedorResponseDTO;
    }
}
