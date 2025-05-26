package sl.sistemaInventarios.servicio.facturacion.interfaces;

import sl.sistemaInventarios.modelo.estado.EstadoEnum;
import sl.sistemaInventarios.modelo.facturacion.DetalleVenta;
import sl.sistemaInventarios.modelo.facturacion.Venta;
import sl.sistemaInventarios.modelo.usuario.Vendedor;

import java.util.List;

public interface IVentaConsultaServicio {
    public List<Venta> mostrarTodaVenta();

    public List<Venta> mostrarVentasPorEstado(EstadoEnum estadoEnum);

    public Venta buscarVentaPorId(Venta venta);

    public List<Venta> buscarVentasPorVendedor(Vendedor vendedor);

    public List<DetalleVenta> obtenerDetallesPorIdVenta(Venta venta);
}