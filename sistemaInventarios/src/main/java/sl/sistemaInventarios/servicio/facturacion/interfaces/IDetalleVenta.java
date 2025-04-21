package sl.sistemaInventarios.servicio.facturacion.interfaces;

import sl.sistemaInventarios.modelo.facturacion.DetalleVenta;
import sl.sistemaInventarios.modelo.producto.Producto;

public interface IDetalleVenta {
    public DetalleVenta guardarDetalleVenta(DetalleVenta detalleVenta);

    public DetalleVenta mostrarVenta(DetalleVenta detalleVenta);

    public Producto sumarVentaProducto(Producto producto);
}
