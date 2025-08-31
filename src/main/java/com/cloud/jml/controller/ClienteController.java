package com.cloud.jml.controller;

import com.cloud.jml.dto.ClienteDTO;
import com.cloud.jml.model.ClienteEntity;
import com.cloud.jml.service.ClienteService;
import lombok.extern.slf4j.Slf4j;
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
    public Optional<ClienteEntity> obtenerClientePorIdentificacion(@RequestBody ClienteDTO clienteDTO) {
        log.info("📌 Iniciando petición para buscar cliente por identificacion: {}", clienteDTO.getIdentificacion());

        Optional<ClienteEntity> response = clienteService.obtenerClientePorIdentificacion(clienteDTO);

        log.info("📌 Finaliza petición de buscar cliente por identificacion: {}", clienteDTO.getIdentificacion());

        return ResponseEntity.ok(response).getBody();
    }

    @GetMapping("/nombre")
    public Optional<ClienteEntity> obtenerClientePorNombre(@RequestBody ClienteDTO clienteDTO) {
        log.info("📌 Iniciando petición para buscar cliente por nombre: {}", clienteDTO.getNombres());

        Optional<ClienteEntity> response = clienteService.obtenerClientePorNombre(clienteDTO);

        log.info("📌 Finaliza petición de buscar cliente por nombre: {}", clienteDTO.getNombres());

        return ResponseEntity.ok(response).getBody();
    }

    @GetMapping("/apellido")
    public Optional<ClienteEntity> obtenerClientePorApellido(@RequestBody ClienteDTO clienteDTO) {
        log.info("📌 Iniciando petición para buscar cliente por apellido: {}", clienteDTO.getApellidos());

        Optional<ClienteEntity> response = clienteService.obtenerClientePorApellido(clienteDTO);

        log.info("📌 Finaliza petición de buscar cliente por apellido: {}", clienteDTO.getApellidos());

        return ResponseEntity.ok(response).getBody();
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
    public ResponseEntity<Void> eliminarCliente(@RequestBody ClienteDTO clienteDTO) {
        log.info("📌 Iniciando petición para eliminar cliente con identificacion: {}", clienteDTO.getIdentificacion());

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
