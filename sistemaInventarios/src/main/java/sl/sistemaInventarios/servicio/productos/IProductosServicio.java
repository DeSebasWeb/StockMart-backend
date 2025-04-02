package sl.sistemaInventarios.servicio.productos;

import sl.sistemaInventarios.modelo.productos.Productos;

import java.util.List;

public interface IProductosServicio {
    public List<Productos> mostrarProductos();

    public Productos buscarProductoPorId(Integer id);

    public void eliminarProducto(Productos producto);

    public List<Productos> buscarPorCategoria(String categoria);

    public Productos guardarProducto(Productos producto);
}
