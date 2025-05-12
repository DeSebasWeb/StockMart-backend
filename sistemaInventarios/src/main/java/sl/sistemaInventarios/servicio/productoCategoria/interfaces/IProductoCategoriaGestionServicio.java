package sl.sistemaInventarios.servicio.productoCategoria.interfaces;

import sl.sistemaInventarios.modelo.productoCategoria.ProductoCategoria;

public interface IProductoCategoriaGestionServicio {
    public ProductoCategoria guardarOActualizarCategoria(ProductoCategoria productoCategoria);

    public ProductoCategoria softDelete(ProductoCategoria productoCategoria);

    public ProductoCategoria recuperar(ProductoCategoria productoCategoria);

    public void hardDelete(ProductoCategoria productoCategoria);
}
