package sl.sistemaInventarios.servicio.categoriaProducto.interfaces;

import sl.sistemaInventarios.modelo.categoriaProducto.ProductoCategoria;

public interface IProductoCategoriaGestionServicio {
    public ProductoCategoria guardarOActualizarCategoria(ProductoCategoria productoCategoria);

    public ProductoCategoria softDelete(ProductoCategoria productoCategoria);

    public ProductoCategoria recuperar(ProductoCategoria productoCategoria);

    public void hardDelete(ProductoCategoria productoCategoria);
}
