package com.cloud.jml.controller;

import com.cloud.jml.dto.ClienteRequestDTO;
import com.cloud.jml.dto.ClienteResponseDTO;
import com.cloud.jml.exception.ClienteNoEncontradoException;
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
    public ResponseEntity<ClienteResponseDTO> crearCliente(@RequestBody ClienteRequestDTO clienteRequestDTO) {
        log.info("📌 Iniciando petición para crear cliente: {}", clienteRequestDTO.getNombres());

        ClienteResponseDTO response = clienteService.crearCliente(clienteRequestDTO);

        log.info("📌 Finaliza petición para crear cliente: {}", clienteRequestDTO.getNombres());

        return ResponseEntity.ok(response);
    }

    @GetMapping("/listar-todos")
    public ResponseEntity<List<ClienteResponseDTO>> listarClientes() {
        log.info("📌 Iniciando petición para listar todos los clientes");

        List<ClienteResponseDTO> clientes = clienteService.listarClientes();

        log.info("📌 Finaliza petición para listar todos los clientes");

        return ResponseEntity.ok(clientes);
    }

    @GetMapping("/identificacion")
    public ResponseEntity<ClienteResponseDTO> obtenerClientePorIdentificacion(@RequestParam("identificacion") Long identificacion) {
        log.info("📌 Iniciando petición para buscar cliente por identificacion: {}", identificacion);

        Optional<ClienteResponseDTO> response = clienteService.obtenerClientePorIdentificacion(identificacion);

        ResponseEntity<ClienteResponseDTO> clienteResponse;

        if (response.isPresent()) {
            clienteResponse = ResponseEntity.ok(response.get());
            log.info("✅ Cliente encontrado con identificacion: {}", identificacion);
        } else {
            clienteResponse = ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            log.warn("❌ Cliente no encontrado con identificacion: {}", identificacion);
        }

        log.info("📌 Finaliza petición de buscar cliente por identificacion: {}", identificacion);

        return clienteResponse;
    }

    @GetMapping("/nombres")
    public ResponseEntity<List<ClienteResponseDTO>> obtenerClientePorNombres(@RequestParam("nombres") String nombres) {
        log.info("📌 Iniciando petición para buscar cliente por nombres: {}", nombres);

        ClienteRequestDTO clienteRequestDTO = new ClienteRequestDTO();
        clienteRequestDTO.setNombres(nombres);

        List<ClienteResponseDTO> response = clienteService.obtenerClientePorNombres(clienteRequestDTO);

        ResponseEntity<List<ClienteResponseDTO>> clienteResponse;

        if (response.isEmpty()) {
            clienteResponse = ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            log.warn("❌ No se encontraron clientes con nombre: {}", nombres);
        } else {
            clienteResponse = ResponseEntity.ok(response);
            log.info("✅ Clientes encontrados con nombre: {}", nombres);
        }

        log.info("📌 Finaliza petición de buscar cliente por nombres: {}", nombres);

        return clienteResponse;
    }

    @GetMapping("/apellidos")
    public ResponseEntity<List<ClienteResponseDTO>> obtenerClientePorApellidos(@RequestParam("apellidos") String apellidos) {
        log.info("📌 Iniciando petición para buscar cliente por apellidos: {}", apellidos);

        ClienteRequestDTO clienteRequestDTO = new ClienteRequestDTO();
        clienteRequestDTO.setApellidos(apellidos);

        List<ClienteResponseDTO> response = clienteService.obtenerClientePorApellidos(clienteRequestDTO);

        ResponseEntity<List<ClienteResponseDTO>> clienteResponse;

        if (response.isEmpty()) {
            clienteResponse = ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            log.warn("❌ No se encontraron clientes con apellidos: {}", apellidos);
        } else {
            clienteResponse = ResponseEntity.ok(response);
            log.info("✅ Clientes encontrados con apellidos: {}", apellidos);
        }

        log.info("📌 Finaliza petición de buscar cliente por apellidos: {}", apellidos);

        return clienteResponse;
    }

    @PutMapping("/actualizar")
    public ResponseEntity<ClienteResponseDTO> actualizarCliente(@RequestBody ClienteRequestDTO clienteRequestDTO) {
        log.info("📌 Iniciando petición para actualizar cliente con identificacion: {}", clienteRequestDTO.getIdentificacion());

        ClienteResponseDTO response;

        try {
            response = clienteService.actualizarCliente(clienteRequestDTO);
            log.info("📌 Finaliza petición de actualización de Cliente con identificacion: {}", clienteRequestDTO.getIdentificacion());
            return ResponseEntity.ok(response);
        } catch (ClienteNoEncontradoException ex) {
            log.warn("❌ No se pudo actualizar el Cliente. Identificacion no encontrado: {}", clienteRequestDTO.getIdentificacion());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @DeleteMapping("/eliminar-identificacion")
    public ResponseEntity<Void> eliminarCliente(@RequestParam("identificacion") Long identificacion) {
        log.info("📌 Iniciando petición para eliminar cliente con identificacion: {}", identificacion);

        ClienteRequestDTO clienteRequestDTO = new ClienteRequestDTO();
        clienteRequestDTO.setIdentificacion(identificacion);

        try {
            clienteService.eliminarCliente(clienteRequestDTO);
            log.info("📌 Finalizó petición de eliminación de cliente con identificacion: {}", identificacion);
            return ResponseEntity.ok().build();
        } catch (RuntimeException e) {
            log.warn("⚠️ Error al eliminar cliente: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}
