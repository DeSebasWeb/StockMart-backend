package sl.sistemaInventarios.servicio.productos;

import sl.sistemaInventarios.modelo.producto.Producto;

import java.util.List;

public interface IProductosServicio {
    public List<Producto> mostrarProductos();

    public Producto buscarProductoPorId(Integer id);

    public void eliminarProducto(Producto producto);

    public List<Producto> buscarPorCategoria(String categoria);

    public Producto guardarProducto(Producto producto);
}
