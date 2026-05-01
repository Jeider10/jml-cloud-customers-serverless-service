package com.cloud.jml.controller;

import com.cloud.jml.dto.ClienteRequestDTO;
import com.cloud.jml.dto.ClienteResponseDTO;
import com.cloud.jml.service.ClienteService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/clientes")
public class ClienteController {

    private final ClienteService clienteService;

    public ClienteController(ClienteService clienteService) {
        this.clienteService = clienteService;
        log.info("🔥 ClienteController inicializado correctamente.");
    }

    @GetMapping("/list/all")
    public ResponseEntity<List<ClienteResponseDTO>> listarClientes() {
        log.info("📥 [SOLICITUD] Listar todos los clientes");

        List<ClienteResponseDTO> clientes = clienteService.listarClientes();

        if (clientes == null || clientes.isEmpty()) {
            log.warn("📤 [RESPUESTA] No se encontraron clientes");
            return ResponseEntity.noContent().build();
        }

        log.info("📤 [RESPUESTA] Se retornan {} clientes", clientes.size());

        return ResponseEntity.ok(clientes);
    }

    @PostMapping("/register")
    public ResponseEntity<ClienteResponseDTO> crearCliente(@RequestBody @Valid ClienteRequestDTO clienteRequestDTO) {
        log.info("📥 [SOLICITUD] Crear cliente: {}", clienteRequestDTO.getNombres());

        ClienteResponseDTO response = clienteService.crearCliente(clienteRequestDTO);

        if (response == null || response.getIdentificacion() == null) {
            log.warn("📤 [RESPUESTA] Error al crear el cliente: {}", clienteRequestDTO.getNombres());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

        log.info("📤 [RESPUESTA] Cliente creado: {} con identificacion: {}", response.getNombres(), response.getIdentificacion());

        return ResponseEntity.ok(response);
    }

    @GetMapping("/identificacion")
    public ResponseEntity<ClienteResponseDTO> obtenerClientePorIdentificacion(@RequestParam("identificacion") Long identificacion) {
        log.info("📥 [SOLICITUD] Buscar cliente por identificacion: {}", identificacion);

        ClienteRequestDTO clienteRequestDTO = new ClienteRequestDTO();
        clienteRequestDTO.setIdentificacion(identificacion);

        ClienteResponseDTO cliente = clienteService.obtenerClientePorIdentificacion(clienteRequestDTO);

        if (cliente == null || cliente.getIdentificacion() == null) {
            log.warn("📤 [RESPUESTA] Cliente no encontrado con identificacion: {}", identificacion);
            return ResponseEntity.noContent().build();
        }

        log.info("📤 [RESPUESTA] Cliente encontrado con identificacion: {}", identificacion);

        return ResponseEntity.ok(cliente);
    }

    @GetMapping("/nombres")
    public ResponseEntity<List<ClienteResponseDTO>> obtenerClientePorNombres(@RequestParam("nombres") String nombres) {
        log.info("📥 [SOLICITUD] Buscar clientes por nombres: {}", nombres);

        ClienteRequestDTO clienteRequestDTO = new ClienteRequestDTO();
        clienteRequestDTO.setNombres(nombres);

        List<ClienteResponseDTO> clientes = clienteService.obtenerClientePorNombres(clienteRequestDTO);

        if (clientes == null || clientes.isEmpty()) {
            log.warn("📤 [RESPUESTA] Cliente no encontrado con nombre: {}", nombres);
            return ResponseEntity.noContent().build();
        }

        log.info("📤 [RESPUESTA] Se retornan {} clientes con nombres: {}", clientes.size(), nombres);

        return ResponseEntity.ok(clientes);
    }

    @GetMapping("/apellidos")
    public ResponseEntity<List<ClienteResponseDTO>> obtenerClientePorApellidos(@RequestParam("apellidos") String apellidos) {
        log.info("📥 [SOLICITUD] Buscar clientes por apellidos: {}", apellidos);

        ClienteRequestDTO clienteRequestDTO = new ClienteRequestDTO();
        clienteRequestDTO.setApellidos(apellidos);

        List<ClienteResponseDTO> clientes = clienteService.obtenerClientePorApellidos(clienteRequestDTO);

        if (clientes == null || clientes.isEmpty()) {
            log.warn("📤 [RESPUESTA] Cliente no encontrado con apellido: {}", apellidos);
            return ResponseEntity.noContent().build();
        }

        log.info("📤 [RESPUESTA] Se retornan {} clientes con apellidos: {}", clientes.size(), apellidos);

        return ResponseEntity.ok(clientes);
    }

    @GetMapping("/fechaCreacion")
    public ResponseEntity<List<ClienteResponseDTO>> obtenerClientePorFechaCreacion(
            @RequestParam("fechaInicio") String fechaInicio,
            @RequestParam("fechaFin") String fechaFin) {

        log.info("📥 [SOLICITUD] Buscar clientes por rango de fecha de creacion: {} - {}", fechaInicio, fechaFin);

        List<ClienteResponseDTO> clientesFecha = clienteService.obtenerClientePorFechaCreacion(fechaInicio, fechaFin);

        if (clientesFecha == null || clientesFecha.isEmpty()) {
            log.warn("⚠️ [RESPUESTA] No se encontraron clientes en el rango de fechas.");
            return ResponseEntity.noContent().build();
        }

        log.info("📤 [RESPUESTA] Se retornan {} clientes en el rango de fechas.", clientesFecha.size());

        return ResponseEntity.ok(clientesFecha);
    }

    @PutMapping("/update")
    public ResponseEntity<ClienteResponseDTO> actualizarCliente(@RequestBody @Valid ClienteRequestDTO clienteRequestDTO) {
        log.info("📥 [SOLICITUD] Actualizar cliente con identificacion: {}", clienteRequestDTO.getIdentificacion());

        ClienteResponseDTO clienteResponseDTO = clienteService.actualizarCliente(clienteRequestDTO);

        if (clienteResponseDTO == null || clienteResponseDTO.getIdentificacion() == null) {
            log.warn("📤 [RESPUESTA] Error al actualizar el cliente con identificacion: {}", clienteRequestDTO.getIdentificacion());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

        log.info("📤 [RESPUESTA] Cliente actualizado correctamente: {} con identificacion: {}", clienteResponseDTO.getNombres(), clienteResponseDTO.getIdentificacion());

        return ResponseEntity.ok(clienteResponseDTO);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<Void> eliminarCliente(@RequestParam("identificacion") Long identificacion) {
        log.info("📥 [SOLICITUD] Eliminar cliente con identificacion: {}", identificacion);

        ClienteRequestDTO clienteRequestDTO = new ClienteRequestDTO();
        clienteRequestDTO.setIdentificacion(identificacion);

        clienteService.eliminarCliente(clienteRequestDTO);

        log.info("📤 [RESPUESTA] Cliente eliminado correctamente con identificacion: {}", identificacion);

        return ResponseEntity.ok().build();
    }
}
