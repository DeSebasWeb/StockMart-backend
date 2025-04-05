package sl.sistemaInventarios.servicio.categoria;

import sl.sistemaInventarios.modelo.categoria.Categoria;

import java.util.List;

public interface ICategoriaServicio {
    public List<Categoria> mostrarCategorias();

    public Categoria buscarCategoriaPorId(Integer idCategoria);

    public Categoria guardarCategria(Categoria categoria);

    public Categoria inactivarCategoria(Integer idCategoria);

    public Categoria activarCategoria(Integer idCategoria);

    public void eliminarCategoria(Categoria categoria);

}
