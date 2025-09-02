package com.cloud.jml.repository;

import com.cloud.jml.model.ClienteEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ClienteRepository extends JpaRepository<ClienteEntity, Long> {
    Optional<ClienteEntity> findByIdentificacion(String identificacion);

    List<ClienteEntity> findByNombres(String nombres);

    List<ClienteEntity> findByApellidos(String apellidos);
}
