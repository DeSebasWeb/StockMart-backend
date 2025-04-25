package sl.sistemaInventarios.servicio.usuario.interfaces;

import sl.sistemaInventarios.modelo.facturacion.Venta;
import sl.sistemaInventarios.modelo.usuario.Vendedor;

public interface IVendedorGestionServicio {
    public Vendedor guardarDatosVendedor(Vendedor vendedor);

    public Vendedor incrementarVenta(Venta venta);
}
