package sl.sistemaInventarios.servicio.producto.interfaces;

import sl.sistemaInventarios.modelo.facturacion.Venta;
import sl.sistemaInventarios.modelo.producto.Producto;

public interface IProductoGestionServicio {
    public Producto softDelete(Integer idProducto);

    public void hardDelete(Integer idProducto);

    public Producto recuperarProducto(Integer idProducto);

    public Producto guardarOActualizarProducto(Producto producto);

    public void actualizarStock(Venta venta);
}
