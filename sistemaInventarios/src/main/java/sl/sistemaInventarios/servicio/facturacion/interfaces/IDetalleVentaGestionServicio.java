package sl.sistemaInventarios.servicio.facturacion.interfaces;

import sl.sistemaInventarios.modelo.facturacion.DetalleVenta;
import sl.sistemaInventarios.modelo.facturacion.Venta;

import java.util.List;

public interface IDetalleVentaGestionServicio {
    public List<DetalleVenta> guardarDetallesVenta(List<DetalleVenta> detallesVenta, Venta venta);

    public List<DetalleVenta> calcularSubtotalPorProducto(List<DetalleVenta> productosDetalleVenta);
}
