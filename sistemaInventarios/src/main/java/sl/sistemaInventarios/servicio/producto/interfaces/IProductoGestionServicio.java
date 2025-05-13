package sl.sistemaInventarios.servicio.producto.interfaces;

import sl.sistemaInventarios.modelo.facturacion.Venta;
import sl.sistemaInventarios.modelo.producto.Producto;

public interface IProductoGestionServicio {
    public Producto softDelete(Producto producto);

    public void hardDelete(Producto producto);

    public Producto recuperarProducto(Producto producto);

    public Producto guardarOActualizarProducto(Producto producto);

    public void actualizarStock(Venta venta);
}
