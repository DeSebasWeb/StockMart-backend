package com.stockmart.api.repositorio.estado;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.stockmart.api.modelo.estado.Estado;
import com.stockmart.api.modelo.estado.EstadoEnum;

import java.util.Optional;

@Repository
public interface EstadoRepositorio extends JpaRepository<Estado, Integer> {
    Optional<Estado> findByEstado(EstadoEnum estadoEnum);
}
