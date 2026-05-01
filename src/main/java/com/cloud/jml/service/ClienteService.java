package com.cloud.jml.service;

import com.cloud.jml.dto.ClienteRequestDTO;
import com.cloud.jml.dto.ClienteResponseDTO;
import com.cloud.jml.exception.cliente.ClienteDuplicadoException;
import com.cloud.jml.exception.cliente.ClienteNoEncontradoException;
import com.cloud.jml.model.ClienteEntity;
import com.cloud.jml.repository.ClienteRepository;
import com.cloud.jml.utils.cliente.ClienteMapper;
import com.cloud.jml.utils.cliente.ClienteUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

@Slf4j
@Service
public class ClienteService {

    private final ClienteRepository clienteRepository;
    private final ClienteMapper mapper;
    private final ClienteUtils clienteUtils;

    public ClienteService(ClienteRepository clienteRepository, ClienteMapper mapper, ClienteUtils clienteUtils) {
        this.clienteRepository = clienteRepository;
        this.mapper = mapper;
        this.clienteUtils = clienteUtils;
        log.info("🔥 ClienteService inicializado correctamente.");
    }

    @Transactional(readOnly = true)
    public List<ClienteResponseDTO> listarClientes() {
        log.info("🔍 [CONSULTA] Recuperando todos los clientes desde la base de datos");

        List<ClienteEntity> clienteEntity = clienteRepository.findAll();

        if (clienteEntity.isEmpty()) {
            log.warn("⚠️ [RESULTADO] No se encontraron clientes registrados en la base de datos");
            return List.of();
        }

        log.info("📦 [MAPEO] Transformando {} entidades de clientes a DTOs", clienteEntity.size());

        // convertir a stream
        Stream<ClienteEntity> streamClientes = clienteEntity.stream();

        // mapear entidades a DTOs
        Stream<ClienteResponseDTO> streamDto = streamClientes.map(mapper::mapEntityToResponseDto);

        // recolectar en lista
        List<ClienteResponseDTO> clienteResponse = streamDto.toList();

        log.info("✅ [FINALIZADO] Total de clientes mapeados y retornados: {}", clienteResponse.size());

        return clienteResponse;
    }

    @Transactional
    public ClienteResponseDTO crearCliente(ClienteRequestDTO clienteRequestDTO) {
        log.info("🔍 [CONSULTA] Inicio de creacion de cliente: {}", clienteRequestDTO.getNombres());

        Optional<ClienteEntity> clienteExistente = clienteRepository.findByIdentificacion(clienteRequestDTO.getIdentificacion());

        if (clienteExistente.isPresent()) {
            log.warn("❌ [ERROR] Cliente duplicado detectado: {}", clienteRequestDTO.getIdentificacion());
            throw new ClienteDuplicadoException(clienteRequestDTO.getIdentificacion());
        }

        log.info("📦 [MAPEO] Transformando DTO a entidad de cliente");
        ClienteEntity clienteEntity = mapper.mapRequestDtoToEntity(clienteRequestDTO);
        log.info("📦 [MAPEO] Cliente: {} mapeado a entidad con identificacion: {}", clienteEntity.getNombres(), clienteEntity.getIdentificacion());

        ClienteEntity guardarCliente = clienteUtils.guardarClienteBD(clienteEntity);
        log.info("💾 [PERSISTENCIA] Cliente: {} guardado exitosamente con identificacion: {}", guardarCliente.getNombres(), guardarCliente.getIdentificacion());

        log.info("📦 [MAPEO] Transformando entidad de cliente a DTO. (crearCliente)");
        ClienteResponseDTO clienteResponseDTO = mapper.mapEntityToResponseDto(guardarCliente);
        log.info("📦 [MAPEO] Cliente mapeado a DTO. identificacion: {}, nombres: {}, apellidos: {}",
                clienteResponseDTO.getIdentificacion(), clienteResponseDTO.getNombres(), clienteResponseDTO.getApellidos());

        log.info("✅ [FINALIZADO] Cliente creado correctamente: {} con identificacion {}", clienteResponseDTO.getNombres(), clienteResponseDTO.getIdentificacion());

        return clienteResponseDTO;
    }

    @Transactional(readOnly = true)
    public ClienteResponseDTO obtenerClientePorIdentificacion(ClienteRequestDTO clienteRequestDTO) {
        log.info("🔍 [CONSULTA] Iniciando busqueda de cliente con identificacion: {}", clienteRequestDTO.getIdentificacion());

        Optional<ClienteEntity> optionalCliente = clienteRepository.findByIdentificacion(clienteRequestDTO.getIdentificacion());

        if (optionalCliente.isEmpty()) {
            log.warn("❌ [RESULTADO] Cliente no encontrado con identificacion: {}", clienteRequestDTO.getIdentificacion());
            return null;
        }

        ClienteEntity clienteEntity = optionalCliente.get();
        log.info("📦 [ENCONTRADO] Cliente encontrado -> identificacion: {}, nombres: {}, apellidos: {}",
                clienteEntity.getIdentificacion(), clienteEntity.getNombres(), clienteEntity.getApellidos());

        log.info("📦 [MAPEO] Transformando entidad de cliente a DTO. (obtenerClientePorIdentificacion)");
        ClienteResponseDTO clienteResponseDTO = mapper.mapEntityToResponseDto(clienteEntity);
        log.info("📦 [MAPEO] Cliente mapeado a DTO con identificacion: {}", clienteResponseDTO.getIdentificacion());

        log.info("✅ [FINALIZADO] Cliente encontrado con identificacion: {}", clienteResponseDTO.getIdentificacion());

        return clienteResponseDTO;
    }

    @Transactional(readOnly = true)
    public List<ClienteResponseDTO> obtenerClientePorNombres(ClienteRequestDTO clienteRequestDTO) {
        log.info("🔍 [CONSULTA] Iniciando busqueda de clientes por nombres: {}", clienteRequestDTO.getNombres());

        List<ClienteEntity> optionalCliente = clienteRepository.findByNombresContainingIgnoreCase(clienteRequestDTO.getNombres());

        if (optionalCliente.isEmpty()) {
            log.warn("❌ [RESULTADO] No se encontraron clientes con nombre: {}", clienteRequestDTO.getNombres());
            return List.of();
        }

        log.info("📦 [MAPEO] Transformando {} entidades de clientes a DTOs (nombre: {})", optionalCliente.size(), clienteRequestDTO.getNombres());

        // convertir a stream
        Stream<ClienteEntity> streamClientes = optionalCliente.stream();

        // mapear entidades a DTOs
        Stream<ClienteResponseDTO> streamDto = streamClientes.map(mapper::mapEntityToResponseDto);

        // recolectar en lista
        List<ClienteResponseDTO> clienteResponse = streamDto.toList();

        log.info("✅ [FINALIZADO] Clientes encontrados con nombre: {}. Total encontrados: {}", clienteRequestDTO.getNombres(), clienteResponse.size());

        return clienteResponse;
    }

    @Transactional(readOnly = true)
    public List<ClienteResponseDTO> obtenerClientePorApellidos(ClienteRequestDTO clienteRequestDTO) {
        log.info("🔍 [CONSULTA] Inicio de busqueda de clientes por apellido: {}", clienteRequestDTO.getApellidos());

        List<ClienteEntity> clienteEntity = clienteRepository.findByApellidosContainingIgnoreCase(clienteRequestDTO.getApellidos());

        if (clienteEntity.isEmpty()) {
            log.warn("❌ [RESULTADO] No se encontraron clientes con apellido: {}", clienteRequestDTO.getApellidos());
            return List.of();
        }

        log.info("📦 [MAPEO] Transformando {} entidades de clientes a DTOs (apellido: {})", clienteEntity.size(), clienteRequestDTO.getApellidos());

        // convertir a stream
        Stream<ClienteEntity> streamClientes = clienteEntity.stream();

        // mapear entidades a DTOs
        Stream<ClienteResponseDTO> streamDto = streamClientes.map(mapper::mapEntityToResponseDto);

        // recolectar en lista
        List<ClienteResponseDTO> clienteResponse = streamDto.toList();

        log.info("✅ [FINALIZADO] Clientes encontrados con apellido '{}'. Total encontrados: {}", clienteRequestDTO.getApellidos(), clienteResponse.size());

        return clienteResponse;
    }

    @Transactional(readOnly = true)
    public List<ClienteResponseDTO> obtenerClientePorFechaCreacion(String fechaInicio, String fechaFin) {
        log.info("🔍 [CONSULTA] Iniciando busqueda de clientes por rango de fecha de creacion: {} - {}", fechaInicio, fechaFin);

        LocalDateTime inicio = clienteUtils.parsearFechaInicio(fechaInicio);
        LocalDateTime fin = clienteUtils.parsearFechaFin(fechaFin);

        log.info("📅 [RANGO] Buscando clientes entre {} y {}", inicio, fin);

        List<ClienteEntity> clienteEntity = clienteRepository.findByFechaCreacionBetween(inicio, fin);

        if (clienteEntity.isEmpty()) {
            log.warn("❌ [RESULTADO] No se encontraron clientes en el rango de fechas: {} - {}", inicio, fin);
            return List.of();
        }

        List<ClienteResponseDTO> clienteResponse = clienteEntity.stream()
                .map(mapper::mapEntityToResponseDto)
                .toList();

        log.info("✅ [FINALIZADO] Clientes encontrados en rango de fechas. Total: {}", clienteResponse.size());

        return clienteResponse;
    }

    @Transactional
    public ClienteResponseDTO actualizarCliente(ClienteRequestDTO clienteRequestDTO) {
        log.info("🔍 [CONSULTA] Inicio de actualizacion de cliente: {} con identificacion: {}", clienteRequestDTO.getNombres(), clienteRequestDTO.getIdentificacion());

        // Paso 1: Validar existencia
        ClienteEntity clienteEntity = clienteUtils.validarExistenciaCliente(clienteRequestDTO);

        // Paso 2: Actualizar datos
        mapper.actualizarClienteExistente(clienteRequestDTO, clienteEntity);

        // Paso 3: Guardar cambios en la BD
        ClienteEntity actualizado = clienteUtils.guardarClienteBD(clienteEntity);
        log.info("💾 [PERSISTENCIA] Cliente actualizado con identificacion: {}", actualizado.getIdentificacion());

        // Paso 4: Mapear a DTO
        log.info("📦 [MAPEO] Transformando entidad de cliente a DTO. (actualizarCliente)");
        ClienteResponseDTO clienteResponseDTO = mapper.mapEntityToResponseDto(actualizado);
        log.info("📦 [MAPEO] Cliente mapeado a DTO. identificacion: {}, nombres: {}",
                clienteResponseDTO.getIdentificacion(), clienteResponseDTO.getNombres());

        log.info("✅ [FINALIZADO] Actualizacion de cliente completada: {} con identificacion: {}", clienteResponseDTO.getNombres(), clienteResponseDTO.getIdentificacion());

        return clienteResponseDTO;
    }

    @Transactional
    public void eliminarCliente(ClienteRequestDTO clienteRequestDTO) {
        log.info("🔍 [CONSULTA] Inicio de eliminacion de cliente con identificacion: {}", clienteRequestDTO.getIdentificacion());

        Optional<ClienteEntity> clienteExistente = clienteRepository.findByIdentificacion(clienteRequestDTO.getIdentificacion());

        if (clienteExistente.isPresent()) {
            ClienteEntity clienteEntity = clienteExistente.get();
            log.info("📦 [ENCONTRADO] Cliente localizado -> {} con identificacion: {}", clienteEntity.getNombres(), clienteEntity.getIdentificacion());

            clienteUtils.eliminarClienteBD(clienteEntity);
            log.info("🗑️ [ELIMINADO] Cliente eliminado correctamente -> {} con identificacion: {}", clienteEntity.getNombres(), clienteEntity.getIdentificacion());
        } else {
            log.warn("❌ [NO ENCONTRADO] Cliente no encontrado con identificacion: {}", clienteRequestDTO.getIdentificacion());
            throw new ClienteNoEncontradoException(clienteRequestDTO.getIdentificacion());
        }
    }
}
