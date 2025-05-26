package com.stockmart.api.repositorio.tipoUsuario;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.stockmart.api.modelo.tipoUsuario.TipoUsuario;
import com.stockmart.api.modelo.tipoUsuario.TipoUsuarioEnum;

import java.util.Optional;

@Repository
public interface TipoUsuarioRespositorio extends JpaRepository<TipoUsuario, Integer> {
    Optional<TipoUsuario> findByNombre(TipoUsuarioEnum tipoUsuarioEnum);
}
