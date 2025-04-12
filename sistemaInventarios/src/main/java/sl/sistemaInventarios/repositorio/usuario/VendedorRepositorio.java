package sl.sistemaInventarios.repositorio.usuario;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sl.sistemaInventarios.modelo.usuario.Vendedor;

@Repository
public interface VendedorRepositorio extends JpaRepository<Vendedor, Integer> {
}
