package sl.sistemaInventarios.repositorio.categoria;

import org.springframework.data.jpa.repository.JpaRepository;
import sl.sistemaInventarios.modelo.categoria.Categoria;

public interface CategoriaRepositorio extends JpaRepository<Categoria, Integer> {
}
