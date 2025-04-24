package sl.sistemaInventarios.servicio.facturacion.interfaces;

import sl.sistemaInventarios.modelo.estado.EstadoEnum;
import sl.sistemaInventarios.modelo.facturacion.DetalleVenta;
import sl.sistemaInventarios.modelo.facturacion.Venta;
import sl.sistemaInventarios.modelo.usuario.Vendedor;

import java.util.List;

public interface IVentaServicio {
    public Venta guardarVenta(Venta venta);

    public List<Venta> mostrarTodaVenta();

    public List<Venta> mostrarVentasPorEstado(EstadoEnum estadoEnum);

    public Venta buscarVentaPorId(Venta venta);

    public List<Venta> buscarVentasPorVendedor(Vendedor vendedor);

    public Venta softDelete(Venta venta);

    public void hardDelete(Venta venta);

    public Venta recuperar(Venta venta);

    public Vendedor sumarVentaVendedor( Venta venta);

    public List<DetalleVenta> obtenerDetallesPorIdVenta(Venta venta);

    public Venta realizarVenta(Venta venta);

    public void actualizarStock(Venta venta);
}