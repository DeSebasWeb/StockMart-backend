package sl.sistemaInventarios.servicio.producto.interfaces;

import sl.sistemaInventarios.modelo.estado.EstadoEnum;
import sl.sistemaInventarios.modelo.producto.Producto;

import java.util.List;

public interface IProductosLecturaServicio {
    public List<Producto> mostrarProductosPorEstado(EstadoEnum estadoEnum);

    public List<Producto> mostrarTodosLosProductos();

    public Producto buscarProductoPorId(Producto producto);

    public List<Producto> buscarPorCategoria(String categoria);

    public List<Producto> productosMasVendidos(int topN);
}
