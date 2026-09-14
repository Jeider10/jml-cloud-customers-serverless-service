package com.cloud.jml.controller;

import com.cloud.jml.dto.ClientePapeleraResponseDTO;
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

    // ─── Listar todos los activos ─────────────────────────────────────────────
    @GetMapping("/list/all")
    public ResponseEntity<List<ClienteResponseDTO>> listarClientes() {
        log.info("📥 [SOLICITUD] Listar todos los clientes activos");

        List<ClienteResponseDTO> clientes = clienteService.listarClientes();

        if (clientes.isEmpty()) {
            log.warn("📤 [RESPUESTA] No se encontraron clientes activos");
            return ResponseEntity.noContent().build();
        }

        log.info("📤 [RESPUESTA] Se retornan {} clientes", clientes.size());

        return ResponseEntity.ok(clientes);
    }

    // ─── Registrar ────────────────────────────────────────────────────────────
    @PostMapping("/register")
    public ResponseEntity<ClienteResponseDTO> crearCliente(@RequestBody @Valid ClienteRequestDTO clienteRequestDTO) {
        log.info("📥 [SOLICITUD] Crear cliente: {}", clienteRequestDTO.getNombres());

        ClienteResponseDTO response = clienteService.crearCliente(clienteRequestDTO);

        log.info("📤 [RESPUESTA] Cliente creado: {} con identificacion: {}", response.getNombres(), response.getIdentificacion());

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    // ─── Buscar por identificación ────────────────────────────────────────────
    @GetMapping("/identificacion")
    public ResponseEntity<ClienteResponseDTO> obtenerClientePorIdentificacion(@RequestParam("identificacion") Long identificacion) {
        log.info("📥 [SOLICITUD] Buscar cliente por identificacion: {}", identificacion);

        ClienteResponseDTO cliente = clienteService.obtenerClientePorIdentificacion(identificacion);

        log.info("📤 [RESPUESTA] Cliente encontrado con identificacion: {}", identificacion);

        return ResponseEntity.ok(cliente);
    }

    // ─── Buscar por nombres ───────────────────────────────────────────────────
    @GetMapping("/nombres")
    public ResponseEntity<List<ClienteResponseDTO>> obtenerClientePorNombres(@RequestParam("nombres") String nombres) {
        log.info("📥 [SOLICITUD] Buscar clientes por nombres: {}", nombres);

        List<ClienteResponseDTO> clientes = clienteService.obtenerClientePorNombres(nombres);

        if (clientes.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        log.info("📤 [RESPUESTA] Se retornan {} clientes con nombres: {}", clientes.size(), nombres);

        return ResponseEntity.ok(clientes);
    }

    // ─── Buscar por apellidos ─────────────────────────────────────────────────
    @GetMapping("/apellidos")
    public ResponseEntity<List<ClienteResponseDTO>> obtenerClientePorApellidos(@RequestParam("apellidos") String apellidos) {
        log.info("📥 [SOLICITUD] Buscar clientes por apellidos: {}", apellidos);

        List<ClienteResponseDTO> clientes = clienteService.obtenerClientePorApellidos(apellidos);

        if (clientes.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        log.info("📤 [RESPUESTA] Se retornan {} clientes con apellidos: {}", clientes.size(), apellidos);

        return ResponseEntity.ok(clientes);
    }

    // ─── Buscar por dirección ─────────────────────────────────────────────────
    @GetMapping("/direccion")
    public ResponseEntity<List<ClienteResponseDTO>> obtenerClientePorDireccion(@RequestParam("direccion") String direccion) {
        log.info("📥 [SOLICITUD] Buscar clientes por direccion: {}", direccion);

        List<ClienteResponseDTO> clientes = clienteService.obtenerClientePorDireccion(direccion);

        if (clientes.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        log.info("📤 [RESPUESTA] Se retornan {} clientes con direccion: {}", clientes.size(), direccion);

        return ResponseEntity.ok(clientes);
    }

    // ─── Buscar por correo ────────────────────────────────────────────────────
    @GetMapping("/correo")
    public ResponseEntity<List<ClienteResponseDTO>> obtenerClientePorCorreo(@RequestParam("correo") String correo) {
        log.info("📥 [SOLICITUD] Buscar clientes por correo: {}", correo);

        List<ClienteResponseDTO> clientes = clienteService.obtenerClientePorCorreo(correo);

        if (clientes.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        log.info("📤 [RESPUESTA] Se retornan {} clientes con correo: {}", clientes.size(), correo);

        return ResponseEntity.ok(clientes);
    }

    // ─── Buscar por creadoPor ─────────────────────────────────────────────────
    @GetMapping("/creadoPor")
    public ResponseEntity<List<ClienteResponseDTO>> obtenerClientePorCreadoPor(@RequestParam("creadoPor") String creadoPor) {
        log.info("📥 [SOLICITUD] Buscar clientes por creadoPor: {}", creadoPor);

        List<ClienteResponseDTO> clientes = clienteService.obtenerClientePorCreadoPor(creadoPor);

        if (clientes.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        log.info("📤 [RESPUESTA] Se retornan {} clientes creados por: {}", clientes.size(), creadoPor);

        return ResponseEntity.ok(clientes);
    }

    // ─── Buscar por fecha de creación ─────────────────────────────────────────
    @GetMapping("/fechaCreacion")
    public ResponseEntity<List<ClienteResponseDTO>> obtenerClientePorFechaCreacion(
            @RequestParam("fechaInicio") String fechaInicio,
            @RequestParam("fechaFin") String fechaFin) {

        log.info("📥 [SOLICITUD] Buscar clientes por rango de fecha de creacion: {} - {}", fechaInicio, fechaFin);

        List<ClienteResponseDTO> clientes = clienteService.obtenerClientePorFechaCreacion(fechaInicio, fechaFin);

        if (clientes.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        log.info("📤 [RESPUESTA] Se retornan {} clientes en el rango de fechas", clientes.size());

        return ResponseEntity.ok(clientes);
    }

    // ─── Buscar por fecha de actualización ───────────────────────────────────
    @GetMapping("/fechaActualizacion")
    public ResponseEntity<List<ClienteResponseDTO>> obtenerClientePorFechaActualizacion(
            @RequestParam("fechaInicio") String fechaInicio,
            @RequestParam("fechaFin") String fechaFin) {

        log.info("📥 [SOLICITUD] Buscar clientes por rango de fecha de actualizacion: {} - {}", fechaInicio, fechaFin);

        List<ClienteResponseDTO> clientes = clienteService.obtenerClientePorFechaActualizacion(fechaInicio, fechaFin);

        if (clientes.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        log.info("📤 [RESPUESTA] Se retornan {} clientes por fecha de actualizacion", clientes.size());

        return ResponseEntity.ok(clientes);
    }

    // ─── Actualizar ───────────────────────────────────────────────────────────
    @PutMapping("/update")
    public ResponseEntity<ClienteResponseDTO> actualizarCliente(@RequestBody @Valid ClienteRequestDTO clienteRequestDTO) {
        log.info("📥 [SOLICITUD] Actualizar cliente con identificacion: {}", clienteRequestDTO.getIdentificacion());

        ClienteResponseDTO response = clienteService.actualizarCliente(clienteRequestDTO);

        log.info("📤 [RESPUESTA] Cliente actualizado: {} con identificacion: {}", response.getNombres(), response.getIdentificacion());

        return ResponseEntity.ok(response);
    }

    // ─── Soft delete (enviar a papelera) ──────────────────────────────────────
    @DeleteMapping("/delete")
    public ResponseEntity<Void> eliminarCliente(
            @RequestParam("identificacion") Long identificacion,
            @RequestParam("eliminadoPorId") String eliminadoPorId,
            @RequestParam("eliminadoPorNombre") String eliminadoPorNombre) {

        log.info("📥 [SOLICITUD] Enviar a papelera cliente con identificacion: {}", identificacion);

        clienteService.eliminarCliente(identificacion, eliminadoPorId, eliminadoPorNombre);

        log.info("📤 [RESPUESTA] Cliente {} enviado a papelera por: {}", identificacion, eliminadoPorNombre);

        return ResponseEntity.ok().build();
    }

    // ─── Listar papelera ──────────────────────────────────────────────────────
    @GetMapping("/trash")
    public ResponseEntity<List<ClientePapeleraResponseDTO>> listarPapelera() {
        log.info("📥 [SOLICITUD] Listar clientes en papelera");

        List<ClientePapeleraResponseDTO> papelera = clienteService.listarPapelera();

        if (papelera.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        log.info("📤 [RESPUESTA] Se retornan {} clientes en papelera", papelera.size());

        return ResponseEntity.ok(papelera);
    }

    // ─── Restaurar desde papelera ─────────────────────────────────────────────
    @PutMapping("/restore")
    public ResponseEntity<ClienteResponseDTO> restaurarCliente(@RequestParam("identificacion") Long identificacion) {
        log.info("📥 [SOLICITUD] Restaurar cliente con identificacion: {}", identificacion);

        ClienteResponseDTO response = clienteService.restaurarCliente(identificacion);

        log.info("📤 [RESPUESTA] Cliente restaurado con identificacion: {}", identificacion);

        return ResponseEntity.ok(response);
    }

    // ─── Eliminar definitivamente ─────────────────────────────────────────────
    @DeleteMapping("/permanent-delete")
    public ResponseEntity<Void> eliminarDefinitivo(@RequestParam("identificacion") Long identificacion) {
        log.info("📥 [SOLICITUD] Eliminar definitivamente cliente con identificacion: {}", identificacion);

        clienteService.eliminarDefinitivo(identificacion);

        log.info("📤 [RESPUESTA] Cliente {} eliminado definitivamente", identificacion);

        return ResponseEntity.ok().build();
    }
}
