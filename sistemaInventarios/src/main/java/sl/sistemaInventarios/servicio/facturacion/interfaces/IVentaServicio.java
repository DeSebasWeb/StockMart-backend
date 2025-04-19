package sl.sistemaInventarios.servicio.facturacion.interfaces;

import sl.sistemaInventarios.modelo.facturacion.Venta;
import sl.sistemaInventarios.modelo.usuario.Vendedor;

import java.util.List;

public interface IVentaServicio {
    public List<Venta> mostrarVentasActivas();

    public List<Venta> moatrarVentasInactivas();

    public Venta buscarVentaPorId(Venta venta);

    public Venta softDelete(Venta venta);

    public Venta hardDelete(Venta venta);

    public Venta recuperar(Venta venta);

    public Vendedor sumarVentaVendedor(Vendedor vendedor);

    public List<Integer> mostrarEstadisticaVentas();
}
