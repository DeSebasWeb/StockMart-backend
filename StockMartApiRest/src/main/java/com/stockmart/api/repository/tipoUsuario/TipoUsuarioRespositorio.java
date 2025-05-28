package com.stockmart.api.repository.tipoUsuario;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.stockmart.api.entity.tipoUsuario.TipoUsuario;
import com.stockmart.api.entity.tipoUsuario.TipoUsuarioEnum;

import java.util.Optional;

@Repository
public interface TipoUsuarioRespositorio extends JpaRepository<TipoUsuario, Integer> {
    Optional<TipoUsuario> findByNombre(TipoUsuarioEnum tipoUsuarioEnum);
}
