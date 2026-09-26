package com.cloud.jml.repository;

import com.cloud.jml.model.ClienteEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface ClienteRepository extends JpaRepository<ClienteEntity, Long> {

    // ─── Activos (eliminado = false) ─────────────────────────────────────────
    Optional<ClienteEntity> findByIdentificacionAndEliminadoFalse(Long identificacion);

    List<ClienteEntity> findAllByEliminadoFalse();

    List<ClienteEntity> findByNombresContainingIgnoreCaseAndEliminadoFalse(String nombres);

    List<ClienteEntity> findByApellidosContainingIgnoreCaseAndEliminadoFalse(String apellidos);

    List<ClienteEntity> findByDireccionContainingIgnoreCaseAndEliminadoFalse(String direccion);

    List<ClienteEntity> findByCorreoContainingIgnoreCaseAndEliminadoFalse(String correo);

    List<ClienteEntity> findByCreadoPorContainingIgnoreCaseAndEliminadoFalse(String creadoPor);

    List<ClienteEntity> findByFechaCreacionBetweenAndEliminadoFalse(LocalDateTime inicio, LocalDateTime fin);

    List<ClienteEntity> findByFechaActualizacionBetweenAndEliminadoFalse(LocalDateTime inicio, LocalDateTime fin);

    // ─── Papelera (eliminado = true) ─────────────────────────────────────────
    List<ClienteEntity> findAllByEliminadoTrue();

    Optional<ClienteEntity> findByIdentificacionAndEliminadoTrue(Long identificacion);

    List<ClienteEntity> findByFechaEliminacionBetweenAndEliminadoTrue(LocalDateTime inicio, LocalDateTime fin);

    List<ClienteEntity> findByEliminadoPorIdContainingIgnoreCaseAndEliminadoTrue(String eliminadoPorId);

    // ─── Verificar duplicado ignorando eliminados ─────────────────────────────
    Optional<ClienteEntity> findByIdentificacion(Long identificacion);
}
