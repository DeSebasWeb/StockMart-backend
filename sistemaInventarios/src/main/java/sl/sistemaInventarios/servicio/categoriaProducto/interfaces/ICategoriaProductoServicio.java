package sl.sistemaInventarios.servicio.categoriaProducto.interfaces;

import sl.sistemaInventarios.modelo.categoriaProducto.ProductoCategoria;
import sl.sistemaInventarios.modelo.estado.EstadoEnum;

import java.util.List;

public interface ICategoriaProductoServicio {
    public List<ProductoCategoria> mostrarTodasCategorias();

    public List<ProductoCategoria> mostrarCategoriasEstado(EstadoEnum estadoEnum);

    public ProductoCategoria buscarCategoriaPorId(ProductoCategoria productoCategoria);

    public ProductoCategoria guardarOActualizarCategoria(ProductoCategoria productoCategoria);

    public ProductoCategoria softDelete(ProductoCategoria productoCategoria);

    public ProductoCategoria recuperar(ProductoCategoria productoCategoria);

    public void hardDelete(ProductoCategoria productoCategoria);
}
