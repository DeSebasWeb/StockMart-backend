package sl.sistemaInventarios.servicio.productoCategoria.interfaces;

import sl.sistemaInventarios.modelo.productoCategoria.ProductoCategoria;
import sl.sistemaInventarios.modelo.estado.EstadoEnum;

import java.util.List;

public interface IProductoCategoriaLecturaServicio {
    public List<ProductoCategoria> mostrarTodasCategorias();

    public List<ProductoCategoria> mostrarCategoriasEstado(EstadoEnum estadoEnum);

    public ProductoCategoria buscarCategoriaPorId(ProductoCategoria productoCategoria);
}
