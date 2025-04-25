package sl.sistemaInventarios.servicio.facturacion.clases;

import org.springframework.beans.factory.annotation.Autowired;
import sl.sistemaInventarios.modelo.facturacion.DetalleVenta;
import sl.sistemaInventarios.modelo.facturacion.Venta;
import sl.sistemaInventarios.repositorio.facturacion.DetalleVentaRepositorio;
import sl.sistemaInventarios.servicio.facturacion.interfaces.IDetalleVentaConsultaServicio;

import java.util.List;

public class DetalleVentaConsultaServicio implements IDetalleVentaConsultaServicio {
    @Autowired
    private DetalleVentaRepositorio detalleVentaRepositorio;

    @Override
    public DetalleVenta mostrarDetalleVentaPorId(Integer idDetalle) {
        return this.detalleVentaRepositorio.findById(idDetalle).orElse(null);
    }

    @Override
    public List<DetalleVenta> mostrarDetallesPorVenta(Venta venta) {
        return this.detalleVentaRepositorio.findByVenta(venta);
    }

    @Override
    public List<DetalleVenta> mostrarTodosDetalles() {
        return this.detalleVentaRepositorio.findAll();
    }
}
