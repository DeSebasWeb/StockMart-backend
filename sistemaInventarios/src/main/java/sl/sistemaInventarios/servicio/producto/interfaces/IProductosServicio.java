package sl.sistemaInventarios.servicio.producto.interfaces;

import sl.sistemaInventarios.modelo.estado.EstadoEnum;
import sl.sistemaInventarios.modelo.producto.Producto;

import java.util.List;

public interface IProductosServicio {
    public List<Producto> mostrarProductosPorEstado(EstadoEnum estadoEnum);

    public List<Producto> mostrarTodosLosProductos();

    public Producto buscarProductoPorId(Producto producto);

    public List<Producto> buscarPorCategoria(String categoria);

    public Producto softDelete(Producto producto);

    public void hardDelete(Producto producto);

    public Producto recuperarProducto(Producto producto);

    public Producto guardarProducto(Producto producto);

    public List<Producto> productosMasVendidos(int topN);
}
