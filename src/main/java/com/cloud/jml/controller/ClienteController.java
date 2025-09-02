package com.cloud.jml.controller;

import com.cloud.jml.dto.ClienteDTO;
import com.cloud.jml.model.ClienteEntity;
import com.cloud.jml.service.ClienteService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("/clientes")
@CrossOrigin(origins = "http://localhost:8080")
public class ClienteController {

    private final ClienteService clienteService;

    public ClienteController(ClienteService clienteService) {
        this.clienteService = clienteService;
    }

    @PostMapping("/register")
    public ResponseEntity<ClienteDTO> crearCliente(@RequestBody ClienteDTO clienteDTO) {
        log.info("📌 Iniciando petición para crear cliente: {}", clienteDTO.getNombres());

        ClienteDTO response = clienteService.crearCliente(clienteDTO);

        log.info("📌 Finaliza petición para crear cliente: {}", clienteDTO.getNombres());

        return ResponseEntity.ok(response);
    }

    @GetMapping("/identificacion")
    public ResponseEntity<ClienteEntity> obtenerClientePorIdentificacion(@RequestParam("identificacion") String identificacion) {
        log.info("📌 Iniciando petición para buscar cliente por identificacion: {}", identificacion);

        ClienteDTO clienteDTO = new ClienteDTO();
        clienteDTO.setIdentificacion(identificacion);

        Optional<ClienteEntity> response = clienteService.obtenerClientePorIdentificacion(clienteDTO);

        log.info("📌 Finaliza petición de buscar cliente por identificacion: {}", identificacion);

        return response.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @GetMapping("/nombres")
    public ResponseEntity<List<ClienteEntity>> obtenerClientePorNombres(@RequestParam("nombres") String nombres) {
        log.info("📌 Iniciando petición para buscar cliente por nombres: {}", nombres);

        ClienteDTO clienteDTO = new ClienteDTO();
        clienteDTO.setNombres(nombres);

        List<ClienteEntity> response = clienteService.obtenerClientePorNombres(clienteDTO);

        log.info("📌 Finaliza petición de buscar cliente por nombres: {}", nombres);

        return response.isEmpty()
                ? ResponseEntity.status(HttpStatus.NOT_FOUND).build()
                : ResponseEntity.ok(response);
    }

    @GetMapping("/apellidos")
    public ResponseEntity<List<ClienteEntity>> obtenerClientePorApellidos(@RequestParam("apellidos") String apellidos) {
        log.info("📌 Iniciando petición para buscar cliente por apellidos: {}", apellidos);

        ClienteDTO clienteDTO = new ClienteDTO();
        clienteDTO.setApellidos(apellidos);

        List<ClienteEntity> response = clienteService.obtenerClientePorApellidos(clienteDTO);

        log.info("📌 Finaliza petición de buscar cliente por apellidos: {}", apellidos);

        return response.isEmpty()
                ? ResponseEntity.status(HttpStatus.NOT_FOUND).build()
                : ResponseEntity.ok(response);
    }

    @GetMapping("/listar-todos")
    public ResponseEntity<List<ClienteEntity>> listarClientes() {
        log.info("📌 Iniciando petición para listar todos los clientes");

        List<ClienteEntity> clientes = clienteService.listarClientes();

        log.info("📌 Finaliza petición para listar todos los clientes");

        return ResponseEntity.ok(clientes);
    }

    @PutMapping("/actualizar")
    public ResponseEntity<ClienteDTO> actualizarCliente(@RequestBody ClienteDTO clienteDTO) {
        log.info("📌 Iniciando petición para actualizar cliente con identificacion: {}", clienteDTO.getIdentificacion());

        ClienteDTO response = clienteService.actualizarCliente(clienteDTO);

        if (response == null) {
            log.warn("⚠️ No se pudo actualizar el cliente. Identificacion no encontrada: {}", clienteDTO.getIdentificacion());
            return ResponseEntity.notFound().build();
        }

        log.info("📌 Finaliza petición de actualización de cliente con identificacion: {}", clienteDTO.getIdentificacion());
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/eliminar-identificacion")
    public ResponseEntity<Void> eliminarCliente(@RequestParam("identificacion") String identificacion) {
        log.info("📌 Iniciando petición para eliminar cliente con identificacion: {}", identificacion);

        ClienteDTO clienteDTO = new ClienteDTO();
        clienteDTO.setIdentificacion(identificacion);

        try {
            clienteService.eliminarCliente(clienteDTO);
            log.info("📌 Finalizó petición de eliminación de cliente con identificacion: {}", clienteDTO.getIdentificacion());
            return ResponseEntity.noContent().build(); // 204 No Content
        } catch (RuntimeException e) {
            log.warn("⚠️ Error al eliminar cliente: {}", e.getMessage());
            return ResponseEntity.notFound().build(); // 404 Not Found
        }
    }
}
