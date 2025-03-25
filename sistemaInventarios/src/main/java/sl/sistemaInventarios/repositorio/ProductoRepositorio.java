package sl.sistemaInventarios.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;
import sl.sistemaInventarios.modelo.Producto;

public interface ProductoRepositorio extends JpaRepository<Producto, Integer> {
}
