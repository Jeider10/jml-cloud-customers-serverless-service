package com.cloud.jml.repository;

import com.cloud.jml.model.ClienteEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface ClienteRepository extends JpaRepository<ClienteEntity, Long> {
    Optional<ClienteEntity> findByIdentificacion(Long identificacion);

    List<ClienteEntity> findByNombres(String nombres);

    List<ClienteEntity> findByNombresContainingIgnoreCase(String nombres);

    List<ClienteEntity> findByApellidos(String apellidos);

    List<ClienteEntity> findByApellidosContainingIgnoreCase(String apellidos);

    List<ClienteEntity> findByFechaCreacionBetween(LocalDateTime inicio, LocalDateTime fin);

    List<ClienteEntity> findByDireccionContainingIgnoreCase(String direccion);
}
