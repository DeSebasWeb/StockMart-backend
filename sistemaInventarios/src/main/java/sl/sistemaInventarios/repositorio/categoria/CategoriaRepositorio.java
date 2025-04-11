package sl.sistemaInventarios.repositorio.categoria;

import org.springframework.data.jpa.repository.JpaRepository;
import sl.sistemaInventarios.modelo.categoriaProducto.ProductoCategoria;

public interface CategoriaRepositorio extends JpaRepository<ProductoCategoria, Integer> {
}
