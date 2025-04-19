package sl.sistemaInventarios.repositorio.tipoUsuario;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sl.sistemaInventarios.modelo.tipoUsuario.TipoUsuario;

@Repository
public interface TipoUsuarioRespositorio extends JpaRepository<TipoUsuario, Integer> {

}
