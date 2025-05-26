package sl.sistemaInventarios.servicio.facturacion.interfaces;

import sl.sistemaInventarios.modelo.facturacion.DetalleVenta;
import sl.sistemaInventarios.modelo.facturacion.Venta;

import java.util.List;

public interface IDetalleVentaConsultaServicio {
    public DetalleVenta mostrarDetalleVentaPorId(Integer idDetalle);

    public List<DetalleVenta> mostrarDetallesPorVenta(Venta venta);

    public List<DetalleVenta> mostrarTodosDetalles();
}