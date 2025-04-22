package sl.sistemaInventarios.repositorio.categoriaProducto;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sl.sistemaInventarios.modelo.categoriaProducto.ProductoCategoria;
import sl.sistemaInventarios.modelo.estado.EstadoEnum;

import java.util.List;

@Repository
public interface ProductoCategoriaRepositorio extends JpaRepository<ProductoCategoria, Integer> {
    List<ProductoCategoria> findByEstado_Estado(EstadoEnum estadoEnum);
}
