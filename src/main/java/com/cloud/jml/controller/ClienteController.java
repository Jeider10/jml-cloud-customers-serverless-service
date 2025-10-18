package com.cloud.jml.controller;

import com.cloud.jml.dto.ClienteRequestDTO;
import com.cloud.jml.dto.ClienteResponseDTO;
import com.cloud.jml.service.ClienteService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/clientes")
@CrossOrigin(origins = "http://localhost:8080")
public class ClienteController {

    private final ClienteService clienteService;

    public ClienteController(ClienteService clienteService) {
        this.clienteService = clienteService;
    }

    @GetMapping("/listar-todos")
    public ResponseEntity<List<ClienteResponseDTO>> listarClientes() {
        log.info("📌 Iniciando petición para listar todos los clientes");

        List<ClienteResponseDTO> clientes = clienteService.listarClientes();

        log.info("📌 Finaliza petición para listar todos los clientes");

        return ResponseEntity.ok(clientes);
    }

    @PostMapping("/register")
    public ResponseEntity<ClienteResponseDTO> crearCliente(@RequestBody ClienteRequestDTO clienteRequestDTO) {
        log.info("📌 Iniciando petición para crear cliente: {}", clienteRequestDTO.getNombres());

        ClienteResponseDTO response = clienteService.crearCliente(clienteRequestDTO);

        log.info("📌 Finaliza petición para crear cliente: {}", clienteRequestDTO.getNombres());

        return ResponseEntity.ok(response);
    }

    @GetMapping("/identificacion")
    public ResponseEntity<ClienteResponseDTO> obtenerClientePorIdentificacion(@RequestParam("identificacion") Long identificacion) {
        log.info("📌 Petición recibida para buscar cliente por identificación: {}", identificacion);

        ClienteRequestDTO clienteRequestDTO = new ClienteRequestDTO();
        clienteRequestDTO.setIdentificacion(identificacion);

        ClienteResponseDTO cliente = clienteService.obtenerClientePorIdentificacion(clienteRequestDTO);

        log.info("✅ Petición finalizada para cliente con identificación: {}", identificacion);

        return ResponseEntity.ok(cliente);
    }

    @GetMapping("/nombres")
    public ResponseEntity<List<ClienteResponseDTO>> obtenerClientePorNombres(@RequestParam("nombres") String nombres) {
        log.info("📌 Iniciando petición para buscar clientes por nombres: {}", nombres);

        ClienteRequestDTO clienteRequestDTO = new ClienteRequestDTO();
        clienteRequestDTO.setNombres(nombres);

        List<ClienteResponseDTO> clientes = clienteService.obtenerClientePorNombres(clienteRequestDTO);

        log.info("📌 Finaliza petición para buscar clientes por nombres: {}", nombres);

        return ResponseEntity.ok(clientes);
    }

    @GetMapping("/apellidos")
    public ResponseEntity<List<ClienteResponseDTO>> obtenerClientePorApellidos(@RequestParam("apellidos") String apellidos) {
        log.info("📌 Iniciando petición para buscar cliente por apellidos: {}", apellidos);

        ClienteRequestDTO clienteRequestDTO = new ClienteRequestDTO();
        clienteRequestDTO.setApellidos(apellidos);

        List<ClienteResponseDTO> responseCliente = clienteService.obtenerClientePorApellidos(clienteRequestDTO);

        log.info("📌 Finaliza petición de buscar cliente por apellidos: {}", apellidos);

        return ResponseEntity.ok(responseCliente);
    }

    @PutMapping("/actualizar")
    public ResponseEntity<ClienteResponseDTO> actualizarCliente(@RequestBody ClienteRequestDTO clienteRequestDTO) {
        log.info("📌 Iniciando petición para actualizar cliente con identificacion: {}", clienteRequestDTO.getIdentificacion());

        ClienteResponseDTO clienteResponseDTO = clienteService.actualizarCliente(clienteRequestDTO);

        log.info("📌 Finaliza petición de actualización de Cliente con identificacion: {}", clienteRequestDTO.getIdentificacion());

        return ResponseEntity.ok(clienteResponseDTO);
    }

    @DeleteMapping("/eliminar-identificacion")
    public ResponseEntity<Void> eliminarCliente(@RequestParam("identificacion") Long identificacion) {
        log.info("📌 Iniciando petición para eliminar cliente con identificacion: {}", identificacion);

        ClienteRequestDTO clienteRequestDTO = new ClienteRequestDTO();
        clienteRequestDTO.setIdentificacion(identificacion);

        clienteService.eliminarCliente(clienteRequestDTO);

        log.info("📌 Finalizó petición de eliminación de cliente con identificacion: {}", identificacion);

        return ResponseEntity.ok().build();
    }
}
