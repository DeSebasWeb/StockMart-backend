package sl.sistemaInventarios.servicio.producto.interfaces;

import sl.sistemaInventarios.modelo.producto.Producto;

import java.util.List;

public interface IProductosServicio {
    public List<Producto> mostrarProductosActivos();

    public List<Producto> mostrarProductosInactivos();

    public Producto buscarProductoPorId(Integer id);

    public List<Producto> buscarPorCategoria(String categoria);

    public Producto softDelete(Producto producto);

    public Producto hardDelete(Producto producto);

    public Producto recuperarProducto(Producto producto);

    public Producto guardarProducto(Producto producto);

    public Integer estadisticaProducto(Producto producto);
}
