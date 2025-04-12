package sl.sistemaInventarios.repositorio.usuario;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sl.sistemaInventarios.modelo.usuario.TipoUsuario;

import java.util.Optional;

@Repository
public interface TipoUsuarioRepositorio extends JpaRepository<TipoUsuario, Integer> {
    Optional<TipoUsuario> findByNombre(String nombre);
}
