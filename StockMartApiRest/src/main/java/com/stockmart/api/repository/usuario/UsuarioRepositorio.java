package com.stockmart.api.repository.usuario;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.stockmart.api.entity.estado.EstadoEnum;
import com.stockmart.api.entity.usuario.Usuario;

import java.util.List;
import java.util.Optional;

@Repository
public interface UsuarioRepositorio extends JpaRepository<Usuario, Integer> {
    Optional<Usuario> findByCedula(Long cedula);
    List<Usuario> findByEstado_Estado(EstadoEnum estadoEnum);
    Optional<Usuario> findByCorreo(String correo);
}
