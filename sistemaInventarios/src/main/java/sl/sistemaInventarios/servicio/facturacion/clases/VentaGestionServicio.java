package sl.sistemaInventarios.servicio.facturacion.clases;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sl.sistemaInventarios.modelo.facturacion.DetalleVenta;
import sl.sistemaInventarios.modelo.facturacion.Venta;
import sl.sistemaInventarios.repositorio.facturacion.VentaRepositorio;
import sl.sistemaInventarios.servicio.estado.clases.IEstadoGestionServicio;
import sl.sistemaInventarios.servicio.facturacion.interfaces.IVentaGestionServicio;
import sl.sistemaInventarios.servicio.producto.clases.ProductoGestionServicio;
import sl.sistemaInventarios.servicio.producto.clases.ProductoLecturaServicio;
import sl.sistemaInventarios.servicio.usuario.clases.IVendedorGestionServicio;

import java.util.List;

@Service
@Transactional
public class VentaGestionServicio implements IVentaGestionServicio {

    private final VentaRepositorio ventaRepositorio;

    private final IEstadoGestionServicio estadoServicio;

    private final ProductoLecturaServicio productoLecturaServicio;

    private final VentaConsultaServicio ventaConsultaServicio;

    private final IVendedorGestionServicio IVendedorGestionServicio;

    private final DetalleVentaGestionServicio detalleVentaGestionServicio;

    private final ProductoGestionServicio productoGestionServicio;
    @Autowired
    public VentaGestionServicio(IEstadoGestionServicio estadoServicio, ProductoLecturaServicio productoLecturaServicio, VentaRepositorio ventaRepositorio, VentaConsultaServicio ventaConsultaServicio, IVendedorGestionServicio IVendedorGestionServicio, DetalleVentaGestionServicio detalleVentaGestionServicio, ProductoGestionServicio productoGestionServicio) {
        this.estadoServicio = estadoServicio;
        this.productoLecturaServicio = productoLecturaServicio;
        this.ventaRepositorio = ventaRepositorio;
        this.ventaConsultaServicio = ventaConsultaServicio;
        this.IVendedorGestionServicio = IVendedorGestionServicio;
        this.detalleVentaGestionServicio = detalleVentaGestionServicio;
        this.productoGestionServicio = productoGestionServicio;
    }


    @Override
    public Venta guardarVenta(Venta venta) {
        Venta ventaGuardada = this.ventaRepositorio.save(venta);
        return  ventaGuardada;
    }

    @Override
    public Venta softDelete(Venta venta) {
        Venta ventaEncontrada = this.ventaConsultaServicio.buscarVentaPorId(venta);
        if (ventaEncontrada.getEstado() == this.estadoServicio.estaEstadoActivo()){
            ventaEncontrada.setEstado(this.estadoServicio.estaEstadoInactivo());
            Venta ventaGuardada = this.guardarVenta(ventaEncontrada);
            return ventaGuardada;
        }else {
            return null;
        }
    }

    @Override
    public void hardDelete(Venta venta) {
        if (venta.getEstado() == this.estadoServicio.estaEstadoInactivo()){
            this.ventaRepositorio.delete(venta);
        }else {
            throw new RuntimeException("La venta tiene estado ACTIVO, si quiere eliminarla, primero pongala en estado INACTIVO");
        }
    }

    @Override
    public Venta recuperar(Venta venta) {
        Venta ventaEncontrada = this.ventaConsultaServicio.buscarVentaPorId(venta);
        if (ventaEncontrada == null){
            throw new RuntimeException("La venta que trata de recuperar no existe");
        }
        if (ventaEncontrada.getEstado() == this.estadoServicio.estaEstadoInactivo()){
            ventaEncontrada.setEstado(this.estadoServicio.estaEstadoActivo());
            Venta ventaGuardada = this.guardarVenta(ventaEncontrada);
            return ventaEncontrada;
        }else {
            throw new RuntimeException("La venta esta activa, no es necesario recuperarla");
        }
    }

    @Override
    public Venta calcularTotalVenta(Venta venta, List<DetalleVenta> detalleVentas) {
        Double total = 0.0;
        if (detalleVentas.isEmpty()){
            throw new IllegalArgumentException("No hay ningun producto para calcular");
        }else {
            for (DetalleVenta productoDetalleVendido : detalleVentas){
                total += productoDetalleVendido.getSubtotal();
            }
            venta.setTotal(total);
            return venta;
        }
    }

    @Override
    public Venta realizarVenta(List<DetalleVenta> detalleVentas, Venta venta) {
        Venta ventaGuardada = this.guardarVenta(venta);
        List<DetalleVenta> detallesGuardados = this.detalleVentaGestionServicio.guardarDetallesVenta(detalleVentas, ventaGuardada);
        Venta ventaCompleta = this.calcularTotalVenta(venta, detallesGuardados);
        this.IVendedorGestionServicio.incrementarVenta(venta);
        this.productoGestionServicio.actualizarStock(venta);
        return ventaCompleta;
    }


}
