package com.cloud.jml.utils.cliente;

import com.cloud.jml.dto.ClienteRequestDTO;
import com.cloud.jml.exception.cliente.ClienteDeletionException;
import com.cloud.jml.exception.cliente.ClienteNoEncontradoException;
import com.cloud.jml.exception.cliente.ClientePersistenceException;
import com.cloud.jml.model.ClienteEntity;
import com.cloud.jml.repository.ClienteRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Slf4j
@Component // 🔹 Anotacion para indicar que es un componente de Spring
public class ClienteUtils {

    private final ClienteRepository clienteRepository;

    public ClienteUtils(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
        log.info("🔥 ClienteUtils inicializado correctamente.");
    }

    public ClienteEntity validarExistenciaCliente(ClienteRequestDTO clienteRequestDTO) {
        log.info("🔍 [SOLICITUD] Validando existencia de cliente: identificacion={}", clienteRequestDTO.getIdentificacion());
        Optional<ClienteEntity> optionalCliente = clienteRepository.findByIdentificacion(clienteRequestDTO.getIdentificacion());

        if (optionalCliente.isPresent()) {
            ClienteEntity clienteEntity = optionalCliente.get();
            log.info("✅ [FINALIZADO] Cliente encontrado: identificacion={}", clienteEntity.getIdentificacion());
            return clienteEntity;
        } else {
            log.warn("⚠️ [RESPUESTA] Cliente no encontrado: identificacion={}", clienteRequestDTO.getIdentificacion());
            throw new ClienteNoEncontradoException(clienteRequestDTO.getIdentificacion());
        }
    }

    public ClienteEntity guardarClienteBD(ClienteEntity clienteEntity) {
        try {
            return clienteRepository.save(clienteEntity);

        } catch (DataIntegrityViolationException e) {
            log.error("🚨 Violacion de integridad al guardar el cliente: {}", e.getMessage(), e);
            throw ClientePersistenceException.integrityViolation(e);

        } catch (DataAccessException e) {
            log.error("🚨 Error de acceso a datos al guardar el cliente: {}", e.getMessage(), e);
            throw ClientePersistenceException.dataAccessError(e);

        } catch (Exception e) {
            log.error("🚨 Error inesperado al guardar el cliente: {}", e.getMessage(), e);
            throw ClientePersistenceException.unexpected(e);
        }
    }

    public void eliminarClienteBD(ClienteEntity clienteEntity) {
        try {
            clienteRepository.delete(clienteEntity);

        } catch (DataIntegrityViolationException e) {
            log.error("🚨 Violacion de integridad al eliminar el cliente: {}", e.getMessage(), e);
            throw ClienteDeletionException.integrityViolation(e);

        } catch (DataAccessException e) {
            log.error("🚨 Error de acceso a datos al eliminar el cliente: {}", e.getMessage(), e);
            throw ClienteDeletionException.dataAccessError(e);

        } catch (Exception e) {
            log.error("🚨 Error inesperado al eliminar el cliente: {}", e.getMessage(), e);
            throw ClienteDeletionException.unexpected(e);
        }
    }
}
