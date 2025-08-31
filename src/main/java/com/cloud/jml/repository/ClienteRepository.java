package com.cloud.jml.repository;

import com.cloud.jml.model.ClienteEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ClienteRepository extends JpaRepository<ClienteEntity, Long> {
    Optional<ClienteEntity> findByIdentificacion(String identificacion);

    Optional<ClienteEntity> findByNombres(String nombres);

    Optional<ClienteEntity> findByApellidos(String apellidos);
}
