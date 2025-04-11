package sl.sistemaInventarios.servicio.categoria;

import sl.sistemaInventarios.modelo.categoriaProducto.ProductoCategoria;

import java.util.List;

public interface ICategoriaServicio {
    public List<ProductoCategoria> mostrarCategorias();

    public ProductoCategoria buscarCategoriaPorId(Integer idCategoria);

    public ProductoCategoria guardarCategria(ProductoCategoria productoCategoria);

    public ProductoCategoria inactivarCategoria(Integer idCategoria);

    public ProductoCategoria activarCategoria(Integer idCategoria);

    public void eliminarCategoria(ProductoCategoria productoCategoria);

}
