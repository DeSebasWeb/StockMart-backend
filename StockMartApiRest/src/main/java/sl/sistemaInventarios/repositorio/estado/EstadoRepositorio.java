package sl.sistemaInventarios.repositorio.estado;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sl.sistemaInventarios.modelo.estado.Estado;
import sl.sistemaInventarios.modelo.estado.EstadoEnum;

import java.util.Optional;

@Repository
public interface EstadoRepositorio extends JpaRepository<Estado, Integer> {
    Optional<Estado> findByEstado(EstadoEnum estadoEnum);
}
