package com.cloud.jml.service;

import com.cloud.jml.dto.ClienteRequestDTO;
import com.cloud.jml.dto.ClienteResponseDTO;
import com.cloud.jml.exception.ClienteDuplicadoException;
import com.cloud.jml.exception.ClienteNoEncontradoException;
import com.cloud.jml.model.ClienteEntity;
import com.cloud.jml.repository.ClienteRepository;
import com.cloud.jml.utils.ClienteMapper;
import com.cloud.jml.utils.ClienteUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    @Transactional
    public ClienteResponseDTO crearCliente(ClienteRequestDTO clienteRequestDTO) {
        log.info("📌 Inicio de creación de cliente: {}", clienteRequestDTO.getNombres());

        // Verificar si ya existe por identificación
        Optional<ClienteEntity> byIdentificacion = clienteRepository.findByIdentificacion(clienteRequestDTO.getIdentificacion());

        if (byIdentificacion.isPresent()) {
            log.warn("⚠️ Cliente duplicado: {}", clienteRequestDTO.getIdentificacion());
            throw new ClienteDuplicadoException(clienteRequestDTO.getIdentificacion());
        }

        // Mapeo de DTO a Entity
        ClienteEntity clienteEntity = mapper.mapRequestDtoToEntity(clienteRequestDTO);

        // Guardamos en la base de datos
        ClienteEntity guardado = clienteRepository.save(clienteEntity);
        log.info("✅ Cliente guardado con Identificacion: {}", guardado.getIdentificacion());

        ClienteResponseDTO clienteResponseDTO = mapper.mapEntityToResponseDto(guardado);
        log.info("📌 Finaliza creación de Cliente: {}", clienteResponseDTO.getNombres());

        return clienteResponseDTO;
    }

    @Transactional(readOnly = true)
    public List<ClienteResponseDTO> listarClientes() {
        log.info("📌 Inicio de búsqueda de todos los clientes");

        // Paso 1: Obtener entidades desde la BD
        List<ClienteEntity> clienteEntity = clienteRepository.findAll();

        // Paso 2: Convertir a Stream
        Stream<ClienteEntity> streamClientes = clienteEntity.stream();

        // Paso 3: Mapear cada entidad a DTO
        Stream<ClienteResponseDTO> streamDto = streamClientes.map(mapper::mapEntityToResponseDto);

        // Paso 4: Convertir a lista final
        List<ClienteResponseDTO> clienteResponse = streamDto.toList();

        log.info("📌 Finaliza búsqueda de todos los Clientes. Total encontrados: {}", clienteResponse.size());

        return clienteResponse;
    }

    @Transactional(readOnly = true)
    public Optional<ClienteResponseDTO> obtenerClientePorIdentificacion(Long identificacion) {
        log.info("📌 Inicio de búsqueda de cliente por identificacion: {}", identificacion);

        Optional<ClienteEntity> optionalClienteEntity = clienteRepository.findByIdentificacion(identificacion);

        if (optionalClienteEntity.isPresent()) {
            Optional<ClienteResponseDTO> proveedorResponseDTO = Optional.of(mapper.mapEntityToResponseDto(optionalClienteEntity.get()));
            log.info("✅ Cliente encontrado con Identificación: {}", identificacion);
            return proveedorResponseDTO;
        } else {
            Optional<ClienteResponseDTO> responseDTO = Optional.empty();
            log.info("⚠️ Cliente no encontrado con Identificación: {}", identificacion);
            return responseDTO;
        }
    }

    @Transactional(readOnly = true)
    public List<ClienteResponseDTO> obtenerClientePorNombres(ClienteRequestDTO clienteRequestDTO) {
        log.info("📌 Inicio de búsqueda de cliente por nombres: {}", clienteRequestDTO.getNombres());

        // Paso 1: Buscar entidades por nombre
        List<ClienteEntity> clienteEntity = clienteRepository.findByNombresContainingIgnoreCase(clienteRequestDTO.getNombres());

        // Paso 2: Validar si está vacío
        if (clienteEntity.isEmpty()) {
            log.warn("⚠️ No se encontraron clientes con nombre: {}", clienteRequestDTO.getNombres());
            return List.of(); // Retorna lista vacía
        }

        // Paso 3: Convertir a Stream
        Stream<ClienteEntity> streamClientes = clienteEntity.stream();

        // Paso 4: Mapear cada entidad a DTO
        Stream<ClienteResponseDTO> streamDto = streamClientes.map(mapper::mapEntityToResponseDto);

        // Paso 5: Convertir a lista final
        List<ClienteResponseDTO> clientesResponse = streamDto.toList();

        log.info("📌 Finaliza búsqueda de Clientes por nombre: {}. Total encontrados: {}",
                clienteRequestDTO.getNombres(), clientesResponse.size());

        return clientesResponse;
    }

    @Transactional(readOnly = true)
    public List<ClienteResponseDTO> obtenerClientePorApellidos(ClienteRequestDTO clienteRequestDTO) {
        log.info("📌 Inicio de búsqueda de cliente por apellidos: {}", clienteRequestDTO.getApellidos());

        // Paso 1: Buscar entidades por nombre
        List<ClienteEntity> clienteEntity = clienteRepository.findByApellidosContainingIgnoreCase(clienteRequestDTO.getApellidos());

        // Paso 2: Validar si está vacío
        if (clienteEntity.isEmpty()) {
            log.warn("⚠️ No se encontraron clientes con apellidos: {}", clienteRequestDTO.getApellidos());
            return List.of(); // Retorna lista vacía
        }

        // Paso 3: Convertir a Stream
        Stream<ClienteEntity> streamClientes = clienteEntity.stream();

        // Paso 4: Mapear cada entidad a DTO
        Stream<ClienteResponseDTO> streamDto = streamClientes.map(mapper::mapEntityToResponseDto);

        // Paso 5: Convertir a lista final
        List<ClienteResponseDTO> clientesResponse = streamDto.toList();

        log.info("📌 Finaliza búsqueda de Clientes por apellidos: {}. Total encontrados: {}",
                clienteRequestDTO.getApellidos(), clientesResponse.size());

        return clientesResponse;
    }

    @Transactional
    public ClienteResponseDTO actualizarCliente(ClienteRequestDTO clienteRequestDTO) {
        log.info("📌 Inicio de actualización de cliente: {} con identificacion: {}",
                clienteRequestDTO.getNombres(), clienteRequestDTO.getIdentificacion());

        // Paso 1: Validar existencia
        ClienteEntity clienteEntity = clienteUtils.validarExistenciaCliente(clienteRequestDTO);

        // Paso 2: Actualizar datos
        clienteUtils.actualizarDatosCliente(clienteRequestDTO, clienteEntity);

        // Paso 3: Guardar cambios en la BD
        ClienteEntity actualizado = clienteRepository.save(clienteEntity);
        log.info("✅ Cliente actualizado con identificacion: {}", actualizado.getIdentificacion());

        // Paso 4: Mapear a DTO
        ClienteResponseDTO clienteResponseDTO = mapper.mapEntityToResponseDto(actualizado);
        log.info("📌 Finaliza actualización de Proveedor: {} con Codigo de Sucursal: {}",
                clienteResponseDTO.getNombres(), clienteResponseDTO.getIdentificacion());

        return clienteResponseDTO;
    }

    @Transactional
    public void eliminarCliente(ClienteRequestDTO clienteRequestDTO) {
        log.info("📌 Inicio de eliminación de cliente con identificacion: {}", clienteRequestDTO.getIdentificacion());

        Optional<ClienteEntity> clienteOptional = clienteRepository.findByIdentificacion(clienteRequestDTO.getIdentificacion());

        if (clienteOptional.isPresent()) {
            ClienteEntity clienteEntity = clienteOptional.get();
            clienteRepository.delete(clienteEntity);
            log.info("✅ Cliente eliminado con identificacion: {}", clienteRequestDTO.getIdentificacion());
        } else {
            log.warn("⚠️ Cliente no encontrado con identificacion: {}", clienteRequestDTO.getIdentificacion());
            throw new ClienteNoEncontradoException(clienteRequestDTO.getIdentificacion());
        }
    }
}
