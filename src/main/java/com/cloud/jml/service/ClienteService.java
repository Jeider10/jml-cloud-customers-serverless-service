package com.cloud.jml.service;

import com.cloud.jml.dto.ClienteDTO;
import com.cloud.jml.model.ClienteEntity;
import com.cloud.jml.repository.ClienteRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class ClienteService {

    private final ClienteRepository clienteRepository;

    public ClienteService(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
        log.info("🔥 ClienteService inicializado correctamente.");
    }

    @Transactional
    public ClienteDTO crearCliente(ClienteDTO clienteDTO) {
        log.info("📌 Inicio de creación de cliente: {}", clienteDTO.getNombres());

        // Mapeo de DTO a Entity
        ClienteEntity clienteEntity = mapDtoToEntity(clienteDTO);
        clienteEntity.setFechaCreacion(LocalDateTime.now());
        log.debug("🔹 Cliente mapeado a Entity: {}", clienteEntity);

        // Guardamos en la base de datos
        ClienteEntity guardado = clienteRepository.save(clienteEntity);
        log.info("✅ Cliente guardado con ID: {}", guardado.getId());

        // Convertimos de nuevo a DTO
        ClienteDTO response = mapEntityToDto(guardado);
        log.debug("🔹 Cliente convertido nuevamente a DTO: {}", response);

        log.info("📌 Finalizó creación de cliente: {}", response.getNombres());

        return response;
    }

    @Transactional
    public Optional<ClienteEntity> obtenerClientePorIdentificacion(ClienteDTO clienteDTO) {
        log.info("📌 Inicio de búsqueda de cliente por identificacion: {}", clienteDTO.getIdentificacion());

        Optional<ClienteEntity> byIdentificacion = clienteRepository.findByIdentificacion(clienteDTO.getIdentificacion());

        if (byIdentificacion.isPresent()) {
            log.info("✅ Cliente encontrado con identificacion: {}", byIdentificacion.get().getIdentificacion());
        } else {
            log.warn("⚠️ No se encontró cliente con identificacion: {}: ", byIdentificacion.get().getIdentificacion());
        }

        log.info("📌 Finaliza búsqueda de cliente por identificacion: {}", clienteDTO.getIdentificacion());

        return byIdentificacion;
    }

    @Transactional
    public Optional<ClienteEntity> obtenerClientePorNombre(ClienteDTO clienteDTO) {
        log.info("📌 Inicio de búsqueda de cliente por nombre: {}", clienteDTO.getNombres());

        Optional<ClienteEntity> byNombres = clienteRepository.findByNombres(clienteDTO.getNombres());

        if (byNombres.isPresent()) {
            log.info("✅ Cliente encontrado con nombres: {}", byNombres.get().getNombres());
        } else {
            log.warn("⚠️ No se encontró cliente con nombres: {}: ", byNombres.get().getNombres());
        }

        log.info("📌 Finaliza búsqueda de cliente por nombres: {}", clienteDTO.getNombres());

        return byNombres;
    }

    @Transactional
    public Optional<ClienteEntity> obtenerClientePorApellido(ClienteDTO clienteDTO) {
        log.info("📌 Inicio de búsqueda de cliente por apellido: {}", clienteDTO.getNombres());

        Optional<ClienteEntity> byApellidos = clienteRepository.findByApellidos(clienteDTO.getApellidos());

        if (byApellidos.isPresent()) {
            log.info("✅ Cliente encontrado con apellidos: {}", byApellidos.get().getApellidos());
        } else {
            log.warn("⚠️ No se encontró cliente con apellidos: {}", byApellidos.get().getApellidos());
        }

        log.info("📌 Finaliza búsqueda de cliente por apellidos: {}", clienteDTO.getApellidos());

        return byApellidos;
    }

    @Transactional
    public List<ClienteEntity> listarClientes() {
        log.info("📌 Inicio de búsqueda de todos los clientes");

        List<ClienteEntity> allClientes = clienteRepository.findAll();

        log.info("✅ Se encontraron {} clientes", allClientes.size());

        return allClientes;
    }

    @Transactional
    public ClienteDTO actualizarCliente(ClienteDTO clienteDTO) {
        log.info("📌 Inicio de actualización de cliente: {} con identificacion: {}", clienteDTO.getNombres(), clienteDTO.getIdentificacion());

        Optional<ClienteEntity> clienteOpt = clienteRepository.findByIdentificacion(clienteDTO.getIdentificacion());

        if (clienteOpt.isPresent()) {
            log.info("✅ Cliente: {} encontrado con identificacion: {}", clienteDTO.getNombres(), clienteDTO.getIdentificacion());
        } else {
            log.warn("⚠️ No se encontró el cliente: {} con identificacion: {}", clienteDTO.getNombres(), clienteDTO.getIdentificacion());
            return null; // O lanzar una excepción personalizada
        }

        ClienteEntity clienteEntity = clienteOpt.get();

        actualizarDatosCliente(clienteDTO, clienteEntity);

        ClienteEntity actualizado = clienteRepository.save(clienteEntity);
        log.info("✅ Cliente actualizado con identificacion: {}", actualizado.getIdentificacion());

        ClienteDTO response = mapEntityToDto(actualizado);
        log.debug("🔹 Cliente actualizado convertido a DTO: {}", response);
        log.info("📌 Finalizó actualización de cliente: {} con identificacion: {}", response.getNombres(), response.getIdentificacion());

        return response;
    }

    @Transactional
    public void eliminarCliente(ClienteDTO clienteDTO) {
        log.info("📌 Inicio de eliminación de cliente con identificacion: {}", clienteDTO.getIdentificacion());

        Optional<ClienteEntity> clienteOpt = clienteRepository.findByIdentificacion(clienteDTO.getIdentificacion());

        if (clienteOpt.isPresent()) {
            log.info("✅ Cliente encontrado con identificacion: {}, sera eliminado.", clienteDTO.getIdentificacion());
        } else {
            log.warn("⚠️ No se encontró cliente con identificacion: {}", clienteDTO.getIdentificacion());
            throw new RuntimeException("Cliente no encontrado con identificacion: " + clienteDTO.getIdentificacion());
        }

        clienteRepository.delete(clienteOpt.get());
        log.info("✅ Cliente eliminado con identificacion: {}", clienteDTO.getIdentificacion());
    }


    @Transactional
    private ClienteEntity mapDtoToEntity(ClienteDTO clienteDTO) {
        log.info("📌 Iniciando mapeo DTO a Entity para crear cliente");

        ClienteEntity clienteEntity = new ClienteEntity();

        clienteEntity.setIdentificacion(clienteDTO.getIdentificacion());
        clienteEntity.setNombres(clienteDTO.getNombres());
        clienteEntity.setApellidos(clienteDTO.getApellidos());
        clienteEntity.setTelefono(clienteDTO.getTelefono());
        clienteEntity.setDireccion(clienteDTO.getDireccion());

        log.info("📌 Finalizando mapeo DTO a Entity para crear cliente");

        return clienteEntity;
    }

    private ClienteDTO mapEntityToDto(ClienteEntity clienteEntity) {
        log.info("📌 Iniciando mapeo Entity a DTO para crear cliente");

        ClienteDTO clienteDTO = new ClienteDTO();

        clienteDTO.setIdentificacion(clienteEntity.getIdentificacion());
        clienteDTO.setNombres(clienteEntity.getNombres());
        clienteDTO.setApellidos(clienteEntity.getApellidos());
        clienteDTO.setTelefono(clienteEntity.getTelefono());
        clienteDTO.setDireccion(clienteEntity.getDireccion());

        log.info("📌 Finalizando mapeo Entity a DTO para crear cliente");

        return clienteDTO;
    }

    private void actualizarDatosCliente(ClienteDTO clienteDTO, ClienteEntity clienteEntity) {
        // Actualizamos solo los campos permitidos
        clienteEntity.setIdentificacion(clienteDTO.getIdentificacion());
        clienteEntity.setNombres(clienteDTO.getNombres());
        clienteEntity.setApellidos(clienteDTO.getApellidos());
        clienteEntity.setTelefono(clienteDTO.getTelefono());
        clienteEntity.setDireccion(clienteDTO.getDireccion());

        // Actualizamos la fecha de actualización
        clienteEntity.setFechaActualizacion(LocalDateTime.now());
    }
}
