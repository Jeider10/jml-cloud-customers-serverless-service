package com.cloud.jml.utils.cliente;

import com.cloud.jml.dto.ClienteRequestDTO;
import com.cloud.jml.dto.ClienteResponseDTO;
import com.cloud.jml.model.ClienteEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Slf4j
@Component // 🔹 Anotacion para indicar que es un componente de Spring
public class ClienteMapper {

    private final ClienteFormatearFecha clienteFormatearFecha;

    public ClienteMapper(ClienteFormatearFecha clienteFormatearFecha) {
        this.clienteFormatearFecha = clienteFormatearFecha;
        log.info("🔥 ClienteMapper inicializado correctamente.");
    }

    public ClienteEntity mapRequestDtoToEntity(ClienteRequestDTO clienteRequestDTO) {
        log.info("📦 [MAPEO] Iniciando mapeo DTO → Entity para cliente: identificacion={}", clienteRequestDTO.getIdentificacion());

        ClienteEntity clienteEntity = new ClienteEntity();

        clienteEntity.setIdentificacion(clienteRequestDTO.getIdentificacion());
        clienteEntity.setNombres(clienteRequestDTO.getNombres());
        clienteEntity.setApellidos(clienteRequestDTO.getApellidos());
        clienteEntity.setTelefono(clienteRequestDTO.getTelefono());
        clienteEntity.setDireccion(clienteRequestDTO.getDireccion());
        clienteEntity.setFechaCreacion(LocalDateTime.now());

        log.info("✅ [MAPEO] Mapeo completado DTO → Entity para cliente: identificacion={}", clienteEntity.getIdentificacion());

        return clienteEntity;
    }

    public ClienteResponseDTO mapEntityToResponseDto(ClienteEntity clienteEntity) {
        log.info("📦 [MAPEO] Iniciando mapeo Entity → DTO para cliente: identificacion={}", clienteEntity.getIdentificacion());

        ClienteResponseDTO clienteResponseDTO = new ClienteResponseDTO();

        clienteResponseDTO.setIdentificacion(clienteEntity.getIdentificacion());
        clienteResponseDTO.setNombres(clienteEntity.getNombres());
        clienteResponseDTO.setApellidos(clienteEntity.getApellidos());
        clienteResponseDTO.setTelefono(clienteEntity.getTelefono());
        clienteResponseDTO.setDireccion(clienteEntity.getDireccion());

        // 🕓 Formateo de fechas
        clienteFormatearFecha.asignarFechasFormateadas(clienteEntity, clienteResponseDTO);

        log.info("✅ [MAPEO] Mapeo completado Entity → DTO para cliente: identificacion={}", clienteResponseDTO.getIdentificacion());

        return clienteResponseDTO;
    }

    public void actualizarClienteExistente(ClienteRequestDTO clienteRequestDTO, ClienteEntity clienteEntity) {
        log.info("✏️ [SOLICITUD] Actualizando cliente existente: identificacion={}", clienteEntity.getIdentificacion());

        // Actualizamos solo los campos permitidos
        clienteEntity.setIdentificacion(clienteRequestDTO.getIdentificacion());
        clienteEntity.setNombres(clienteRequestDTO.getNombres());
        clienteEntity.setApellidos(clienteRequestDTO.getApellidos());
        clienteEntity.setTelefono(clienteRequestDTO.getTelefono());
        clienteEntity.setDireccion(clienteRequestDTO.getDireccion());

        // Actualizamos la fecha de actualizacion
        clienteEntity.setFechaActualizacion(LocalDateTime.now());

        log.info("✅ [FINALIZADO] Cliente actualizado correctamente: identificacion={}", clienteEntity.getIdentificacion());
    }
}
