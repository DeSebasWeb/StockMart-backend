package com.stockmart.api.repository.estado;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.stockmart.api.entity.estado.Estado;
import com.stockmart.api.entity.estado.EstadoEnum;

import java.util.Optional;

@Repository
public interface EstadoRepositorio extends JpaRepository<Estado, Integer> {
    Optional<Estado> findByEstado(EstadoEnum estadoEnum);
}
