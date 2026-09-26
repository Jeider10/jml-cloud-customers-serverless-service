package com.cloud.jml.service;

import com.cloud.jml.dto.ClientePapeleraResponseDTO;
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

    // ─── Listar activos ───────────────────────────────────────────────────────
    @Transactional(readOnly = true)
    public List<ClienteResponseDTO> listarClientes() {
        log.info("🔍 [CONSULTA] Recuperando todos los clientes activos");

        List<ClienteEntity> entidades = clienteRepository.findAllByEliminadoFalse();

        if (entidades.isEmpty()) {
            log.warn("⚠️ [RESULTADO] No se encontraron clientes activos");
            return List.of();
        }

        List<ClienteResponseDTO> respuesta = entidades.stream()
                .map(mapper::mapEntityToResponseDto)
                .toList();

        log.info("✅ [FINALIZADO] Total de clientes activos retornados: {}", respuesta.size());

        return respuesta;
    }

    // ─── Crear ────────────────────────────────────────────────────────────────
    @Transactional
    public ClienteResponseDTO crearCliente(ClienteRequestDTO clienteRequestDTO) {
        log.info("🔍 [SOLICITUD] Creando cliente: {}", clienteRequestDTO.getNombres());

        // Verificar duplicado (incluyendo eliminados para evitar reutilizar identificacion)
        Optional<ClienteEntity> existente = clienteRepository.findByIdentificacion(clienteRequestDTO.getIdentificacion());

        if (existente.isPresent() && !existente.get().isEliminado()) {
            log.warn("❌ [DUPLICADO] Cliente activo ya existe con identificacion: {}", clienteRequestDTO.getIdentificacion());
            throw new ClienteDuplicadoException(clienteRequestDTO.getIdentificacion());
        }

        ClienteEntity entidad = mapper.mapRequestDtoToEntity(clienteRequestDTO);
        ClienteEntity guardado = clienteUtils.guardarClienteBD(entidad);

        log.info("💾 [PERSISTENCIA] Cliente creado con identificacion: {}", guardado.getIdentificacion());

        return mapper.mapEntityToResponseDto(guardado);
    }

    // ─── Buscar por identificacion ────────────────────────────────────────────
    @Transactional(readOnly = true)
    public ClienteResponseDTO obtenerClientePorIdentificacion(Long identificacion) {
        log.info("🔍 [CONSULTA] Buscando cliente con identificacion: {}", identificacion);

        ClienteEntity entidad = clienteRepository.findByIdentificacionAndEliminadoFalse(identificacion)
                .orElseThrow(() -> {
                    log.warn("❌ [RESULTADO] Cliente no encontrado: {}", identificacion);
                    return new ClienteNoEncontradoException(identificacion);
                });

        log.info("✅ [FINALIZADO] Cliente encontrado: {}", identificacion);

        return mapper.mapEntityToResponseDto(entidad);
    }

    // ─── Buscar por nombres ───────────────────────────────────────────────────
    @Transactional(readOnly = true)
    public List<ClienteResponseDTO> obtenerClientePorNombres(String nombres) {
        log.info("🔍 [CONSULTA] Buscando clientes por nombres: {}", nombres);

        List<ClienteEntity> entidades = clienteRepository.findByNombresContainingIgnoreCaseAndEliminadoFalse(nombres);

        if (entidades.isEmpty()) {
            log.warn("⚠️ [RESULTADO] No se encontraron clientes con nombre: {}", nombres);
            return List.of();
        }

        return entidades.stream().map(mapper::mapEntityToResponseDto).toList();
    }

    // ─── Buscar por apellidos ─────────────────────────────────────────────────
    @Transactional(readOnly = true)
    public List<ClienteResponseDTO> obtenerClientePorApellidos(String apellidos) {
        log.info("🔍 [CONSULTA] Buscando clientes por apellidos: {}", apellidos);

        List<ClienteEntity> entidades = clienteRepository.findByApellidosContainingIgnoreCaseAndEliminadoFalse(apellidos);

        if (entidades.isEmpty()) {
            log.warn("⚠️ [RESULTADO] No se encontraron clientes con apellido: {}", apellidos);
            return List.of();
        }

        return entidades.stream().map(mapper::mapEntityToResponseDto).toList();
    }

    // ─── Buscar por direccion ─────────────────────────────────────────────────
    @Transactional(readOnly = true)
    public List<ClienteResponseDTO> obtenerClientePorDireccion(String direccion) {
        log.info("🔍 [CONSULTA] Buscando clientes por direccion: {}", direccion);

        List<ClienteEntity> entidades = clienteRepository.findByDireccionContainingIgnoreCaseAndEliminadoFalse(direccion);

        if (entidades.isEmpty()) {
            log.warn("⚠️ [RESULTADO] No se encontraron clientes con direccion: {}", direccion);
            return List.of();
        }

        return entidades.stream().map(mapper::mapEntityToResponseDto).toList();
    }

    // ─── Buscar por correo ────────────────────────────────────────────────────
    @Transactional(readOnly = true)
    public List<ClienteResponseDTO> obtenerClientePorCorreo(String correo) {
        log.info("🔍 [CONSULTA] Buscando clientes por correo: {}", correo);

        List<ClienteEntity> entidades = clienteRepository.findByCorreoContainingIgnoreCaseAndEliminadoFalse(correo);

        if (entidades.isEmpty()) {
            log.warn("⚠️ [RESULTADO] No se encontraron clientes con correo: {}", correo);
            return List.of();
        }

        return entidades.stream().map(mapper::mapEntityToResponseDto).toList();
    }

    // ─── Buscar por creadoPor ─────────────────────────────────────────────────
    @Transactional(readOnly = true)
    public List<ClienteResponseDTO> obtenerClientePorCreadoPor(String creadoPor) {
        log.info("🔍 [CONSULTA] Buscando clientes por creadoPor: {}", creadoPor);

        List<ClienteEntity> entidades = clienteRepository.findByCreadoPorContainingIgnoreCaseAndEliminadoFalse(creadoPor);

        if (entidades.isEmpty()) {
            log.warn("⚠️ [RESULTADO] No se encontraron clientes con creadoPor: {}", creadoPor);
            return List.of();
        }

        return entidades.stream().map(mapper::mapEntityToResponseDto).toList();
    }

    // ─── Buscar por fecha de creacion ─────────────────────────────────────────
    @Transactional(readOnly = true)
    public List<ClienteResponseDTO> obtenerClientePorFechaCreacion(String fechaInicio, String fechaFin) {
        log.info("🔍 [CONSULTA] Buscando clientes por rango de fecha de creacion: {} - {}", fechaInicio, fechaFin);

        LocalDateTime inicio = clienteUtils.parsearFechaInicio(fechaInicio);
        LocalDateTime fin = clienteUtils.parsearFechaFin(fechaFin);

        List<ClienteEntity> entidades = clienteRepository.findByFechaCreacionBetweenAndEliminadoFalse(inicio, fin);

        if (entidades.isEmpty()) {
            log.warn("⚠️ [RESULTADO] No se encontraron clientes en el rango de fechas: {} - {}", inicio, fin);
            return List.of();
        }

        return entidades.stream().map(mapper::mapEntityToResponseDto).toList();
    }

    // ─── Buscar por fecha de actualizacion ───────────────────────────────────
    @Transactional(readOnly = true)
    public List<ClienteResponseDTO> obtenerClientePorFechaActualizacion(String fechaInicio, String fechaFin) {
        log.info("🔍 [CONSULTA] Buscando clientes por rango de fecha de actualizacion: {} - {}", fechaInicio, fechaFin);

        LocalDateTime inicio = clienteUtils.parsearFechaInicio(fechaInicio);
        LocalDateTime fin = clienteUtils.parsearFechaFin(fechaFin);

        List<ClienteEntity> entidades = clienteRepository.findByFechaActualizacionBetweenAndEliminadoFalse(inicio, fin);

        if (entidades.isEmpty()) {
            log.warn("⚠️ [RESULTADO] No se encontraron clientes en el rango de fecha de actualizacion.");
            return List.of();
        }

        return entidades.stream().map(mapper::mapEntityToResponseDto).toList();
    }

    // ─── Actualizar ───────────────────────────────────────────────────────────
    @Transactional
    public ClienteResponseDTO actualizarCliente(ClienteRequestDTO clienteRequestDTO) {
        log.info("🔍 [SOLICITUD] Actualizando cliente con identificacion: {}", clienteRequestDTO.getIdentificacion());

        ClienteEntity entidad = clienteUtils.validarExistenciaCliente(clienteRequestDTO);
        mapper.actualizarClienteExistente(clienteRequestDTO, entidad);
        ClienteEntity actualizado = clienteUtils.guardarClienteBD(entidad);

        log.info("✅ [FINALIZADO] Cliente actualizado con identificacion: {}", actualizado.getIdentificacion());

        return mapper.mapEntityToResponseDto(actualizado);
    }

    // ─── Soft delete (a papelera) ─────────────────────────────────────────────
    @Transactional
    public void eliminarCliente(Long identificacion, String eliminadoPorId, String eliminadoPorNombre) {
        log.info("🔍 [SOLICITUD] Enviando a papelera cliente con identificacion: {}", identificacion);

        ClienteEntity entidad = clienteRepository.findByIdentificacionAndEliminadoFalse(identificacion)
                .orElseThrow(() -> new ClienteNoEncontradoException(identificacion));

        entidad.setEliminado(true);
        entidad.setFechaEliminacion(LocalDateTime.now());
        entidad.setEliminadoPorId(eliminadoPorId);
        entidad.setEliminadoPorNombre(eliminadoPorNombre);

        clienteUtils.guardarClienteBD(entidad);

        log.info("🗑️ [PAPELERA] Cliente {} enviado a papelera por: {}", identificacion, eliminadoPorNombre);
    }

    // ─── Listar papelera ──────────────────────────────────────────────────────
    @Transactional(readOnly = true)
    public List<ClientePapeleraResponseDTO> listarPapelera() {
        log.info("🔍 [CONSULTA] Listando clientes en papelera");

        List<ClienteEntity> entidades = clienteRepository.findAllByEliminadoTrue();

        if (entidades.isEmpty()) {
            log.warn("⚠️ [RESULTADO] No hay clientes en papelera");
            return List.of();
        }

        List<ClientePapeleraResponseDTO> respuesta = entidades.stream()
                .map(mapper::mapEntityToPapeleraDto)
                .toList();

        log.info("✅ [FINALIZADO] Total de clientes en papelera: {}", respuesta.size());

        return respuesta;
    }

    // ─── Restaurar desde papelera ─────────────────────────────────────────────
    @Transactional
    public ClienteResponseDTO restaurarCliente(Long identificacion) {
        log.info("🔍 [SOLICITUD] Restaurando cliente con identificacion: {}", identificacion);

        ClienteEntity entidad = clienteRepository.findByIdentificacionAndEliminadoTrue(identificacion)
                .orElseThrow(() -> {
                    log.warn("❌ [RESULTADO] Cliente no encontrado en papelera: {}", identificacion);
                    return new ClienteNoEncontradoException(identificacion);
                });

        entidad.setEliminado(false);
        entidad.setFechaEliminacion(null);
        entidad.setEliminadoPorId(null);
        entidad.setEliminadoPorNombre(null);
        entidad.setFechaActualizacion(LocalDateTime.now());

        ClienteEntity restaurado = clienteUtils.guardarClienteBD(entidad);
        log.info("✅ [FINALIZADO] Cliente restaurado con identificacion: {}", restaurado.getIdentificacion());

        return mapper.mapEntityToResponseDto(restaurado);
    }

    // ─── Eliminar definitivamente ─────────────────────────────────────────────
    @Transactional
    public void eliminarDefinitivo(Long identificacion) {
        log.info("🔍 [SOLICITUD] Eliminando definitivamente cliente con identificacion: {}", identificacion);

        ClienteEntity entidad = clienteRepository.findByIdentificacionAndEliminadoTrue(identificacion)
                .orElseThrow(() -> {
                    log.warn("❌ [RESULTADO] Cliente no encontrado en papelera para eliminacion definitiva: {}", identificacion);
                    return new ClienteNoEncontradoException(identificacion);
                });

        clienteUtils.eliminarClienteBD(entidad);

        log.info("🗑️ [ELIMINADO] Cliente eliminado definitivamente: {}", identificacion);
    }

    // ─── Filtrar papelera por fecha de eliminacion ────────────────────────────
    @Transactional(readOnly = true)
    public List<ClientePapeleraResponseDTO> listarPapeleraPorFecha(String fechaInicio, String fechaFin) {
        log.info("🔍 [CONSULTA] Filtrando papelera de clientes por fecha: {} - {}", fechaInicio, fechaFin);

        LocalDateTime inicio = clienteUtils.parsearFechaInicio(fechaInicio);
        LocalDateTime fin = clienteUtils.parsearFechaFin(fechaFin);

        List<ClienteEntity> entidades = clienteRepository.findByFechaEliminacionBetweenAndEliminadoTrue(inicio, fin);

        if (entidades.isEmpty()){
            return List.of();
        }

        return entidades.stream().map(mapper::mapEntityToPapeleraDto).toList();
    }

    // ─── Filtrar papelera por quien elimino ───────────────────────────────────
    @Transactional(readOnly = true)
    public List<ClientePapeleraResponseDTO> listarPapeleraPorEliminadoPor(String eliminadoPorId) {
        log.info("🔍 [CONSULTA] Filtrando papelera de clientes por eliminadoPorId: {}", eliminadoPorId);

        List<ClienteEntity> entidades = clienteRepository.findByEliminadoPorIdContainingIgnoreCaseAndEliminadoTrue(eliminadoPorId);

        if (entidades.isEmpty()){
            return List.of();
        }

        return entidades.stream().map(mapper::mapEntityToPapeleraDto).toList();
    }
}
