package sl.sistemaInventarios.repositorio.producto;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sl.sistemaInventarios.modelo.producto.Producto;

import java.util.List;

@Repository
public interface ProductoRepositorio extends JpaRepository<Producto, Integer> {
    List<Producto> findByCategoria(String categoria);
}
