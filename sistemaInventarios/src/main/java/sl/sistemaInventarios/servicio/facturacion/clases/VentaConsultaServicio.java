package sl.sistemaInventarios.servicio.facturacion.clases;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sl.sistemaInventarios.modelo.estado.EstadoEnum;
import sl.sistemaInventarios.modelo.facturacion.DetalleVenta;
import sl.sistemaInventarios.modelo.facturacion.Venta;
import sl.sistemaInventarios.modelo.usuario.Vendedor;
import sl.sistemaInventarios.repositorio.facturacion.VentaRepositorio;
import sl.sistemaInventarios.servicio.estado.clases.IEstadoGestionServicio;
import sl.sistemaInventarios.servicio.facturacion.interfaces.IVentaConsultaServicio;
import sl.sistemaInventarios.servicio.producto.clases.ProductoLecturaServicio;

import java.util.Collections;
import java.util.List;

@Service
public class VentaConsultaServicio implements IVentaConsultaServicio {

    private final VentaRepositorio ventaRepositorio;

    @Autowired
    public VentaConsultaServicio(IEstadoGestionServicio estadoServicio, ProductoLecturaServicio productoServicio, VentaRepositorio ventaRepositorio) {
        this.ventaRepositorio = ventaRepositorio;
    }

    @Override
    public List<Venta> mostrarTodaVenta() {
        return this.ventaRepositorio.findAll();
    }

    @Override
    public List<Venta> mostrarVentasPorEstado(EstadoEnum estadoEnum) {
        List<Venta> ventasPorEstado = this.ventaRepositorio.findByEstado_Estado(estadoEnum);
        return ventasPorEstado;
    }

    @Override
    public Venta buscarVentaPorId(Venta venta) {
        Venta ventaEncontrada = this.ventaRepositorio.findById(venta.getIdVenta()).orElseThrow(() -> new RuntimeException("No se ha encontrado la venta con id: "+ venta.getIdVenta()));
        return ventaEncontrada;
    }

    @Override
    public List<Venta> buscarVentasPorVendedor(Vendedor vendedor) {
        List<Venta> ventasPorVendedor = this.ventaRepositorio.findByVendedor(vendedor);
        return ventasPorVendedor;
    }


    @Transactional
    @Override
    public List<DetalleVenta> obtenerDetallesPorIdVenta(Venta venta) {
        Venta ventaDetalles = this.buscarVentaPorId(venta);
        if (ventaDetalles != null){
            venta.getDetalles().size();
            return venta.getDetalles();
        }
        return Collections.emptyList();
    }
}