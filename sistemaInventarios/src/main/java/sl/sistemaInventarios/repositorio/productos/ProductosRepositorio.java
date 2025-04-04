package sl.sistemaInventarios.repositorio.productos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sl.sistemaInventarios.modelo.productos.Productos;

import java.util.List;

@Repository
public interface ProductosRepositorio extends JpaRepository<Productos, Integer> {
    List<Productos> findByCategoria(String categoria);
}
