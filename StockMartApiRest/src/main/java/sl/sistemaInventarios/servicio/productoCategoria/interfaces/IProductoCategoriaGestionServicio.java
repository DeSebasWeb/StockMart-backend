package sl.sistemaInventarios.servicio.productoCategoria.interfaces;

import sl.sistemaInventarios.modelo.producto.Producto;
import sl.sistemaInventarios.modelo.productoCategoria.ProductoCategoria;

import java.util.List;

public interface IProductoCategoriaGestionServicio {
    public ProductoCategoria guardarOActualizarCategoria(ProductoCategoria productoCategoria);

    public ProductoCategoria softDelete(Integer idCategoria);

    public ProductoCategoria recuperar(Integer idCategoria);

    public void hardDelete(Integer idCategoria);
}
