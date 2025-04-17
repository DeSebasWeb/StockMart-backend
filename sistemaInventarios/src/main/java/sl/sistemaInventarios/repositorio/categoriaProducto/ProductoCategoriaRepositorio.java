package sl.sistemaInventarios.repositorio.categoriaProducto;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sl.sistemaInventarios.modelo.categoriaProducto.ProductoCategoria;
import sl.sistemaInventarios.modelo.estado.EstadoEnum;

import java.util.Optional;

@Repository
public interface ProductoCategoriaRepositorio extends JpaRepository<ProductoCategoria, Integer> {
    Optional<ProductoCategoria> findByEstado(EstadoEnum estado);
}
