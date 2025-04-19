package sl.sistemaInventarios.servicio.categoriaProducto.interfaces;

import sl.sistemaInventarios.modelo.categoriaProducto.ProductoCategoria;

import java.util.List;

public interface ICategoriaProductoServicio {
    public List<ProductoCategoria> mostrarCategoriasActivas();

    public List<ProductoCategoria> mostrarCategoriasInactivas();

    public ProductoCategoria buscarCategoriaPorId(Integer idCategoria);

    public ProductoCategoria guardarCategoria(ProductoCategoria productoCategoria);

    public ProductoCategoria softDelete(ProductoCategoria productoCategoria);

    public ProductoCategoria recuperar(ProductoCategoria productoCategoria);

    public void hardDelete(ProductoCategoria productoCategoria);
}
