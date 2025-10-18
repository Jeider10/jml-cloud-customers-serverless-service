package com.cloud.jml.utils;

import com.cloud.jml.dto.ClienteRequestDTO;
import com.cloud.jml.exception.cliente.ClienteNoEncontradoException;
import com.cloud.jml.exception.cliente.ClientePersistenceException;
import com.cloud.jml.model.ClienteEntity;
import com.cloud.jml.repository.ClienteRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Optional;

@Slf4j
@Component // 🔹 Anotación para indicar que es un componente de Spring
public class ClienteUtils {

    private final ClienteRepository clienteRepository;

    public ClienteUtils(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
        log.info("🔥 ClienteUtils inicializado correctamente.");
    }

    public ClienteEntity guardarClienteBD(ClienteEntity clienteEntity) {
        try {
            return clienteRepository.save(clienteEntity);

        } catch (DataIntegrityViolationException e) {
            log.error("🚨 Violación de integridad al guardar el cliente: {}", e.getMessage(), e);
            throw new ClientePersistenceException("Error de integridad en base de datos al guardar el cliente", e);

        } catch (DataAccessException e) {
            log.error("🚨 Error de acceso a datos al guardar el cliente: {}", e.getMessage(), e);
            throw new ClientePersistenceException("Error al guardar el cliente en la base de datos", e);

        } catch (Exception e) {
            log.error("🚨 Error inesperado al guardar el cliente: {}", e.getMessage(), e);
            throw new ClientePersistenceException("Error inesperado al registrar el cliente", e);
        }
    }

    public void eliminarClienteBD(ClienteEntity clienteEntity) {
        try {
            clienteRepository.delete(clienteEntity);

        } catch (DataIntegrityViolationException e) {
            log.error("🚨 Violación de integridad al eliminar el cliente: {}", e.getMessage(), e);
            throw new ClientePersistenceException("Error de integridad en base de datos al eliminar el cliente", e);

        } catch (DataAccessException e) {
            log.error("🚨 Error de acceso a datos al eliminar el cliente: {}", e.getMessage(), e);
            throw new ClientePersistenceException("Error al eliminar el cliente en la base de datos", e);

        } catch (Exception e) {
            log.error("🚨 Error inesperado al eliminar el cliente: {}", e.getMessage(), e);
            throw new ClientePersistenceException("Error inesperado al eliminar el cliente", e);
        }
    }

    public ClienteEntity validarExistenciaCliente(ClienteRequestDTO clienteRequestDTO) {
        Optional<ClienteEntity> optionalCliente = clienteRepository.findByIdentificacion(clienteRequestDTO.getIdentificacion());

        if (optionalCliente.isPresent()) {
            ClienteEntity clienteEntity = optionalCliente.get();
            log.info("📌 Cliente encontrado con Identificación: {}", clienteRequestDTO.getIdentificacion());
            return clienteEntity;
        } else {
            log.warn("⚠️ Cliente no encontrado con Identificación: {}", clienteRequestDTO.getIdentificacion());
            throw new ClienteNoEncontradoException(clienteRequestDTO.getIdentificacion());
        }
    }

    public void actualizarDatosCliente(ClienteRequestDTO clienteRequestDTO, ClienteEntity clienteEntity) {
        // Actualizamos solo los campos permitidos
        clienteEntity.setIdentificacion(clienteRequestDTO.getIdentificacion());
        clienteEntity.setNombres(clienteRequestDTO.getNombres());
        clienteEntity.setApellidos(clienteRequestDTO.getApellidos());
        clienteEntity.setTelefono(clienteRequestDTO.getTelefono());
        clienteEntity.setDireccion(clienteRequestDTO.getDireccion());

        // Actualizamos la fecha de actualización
        clienteEntity.setFechaActualizacion(LocalDateTime.now());
    }
}
