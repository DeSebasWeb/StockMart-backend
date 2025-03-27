package sl.sistemaInventarios.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;
import sl.sistemaInventarios.modelo.Producto;

import java.util.List;

public interface ProductoRepositorio extends JpaRepository<Producto, Integer> {
    List<Producto> findByCategoria(String categoria);
}
