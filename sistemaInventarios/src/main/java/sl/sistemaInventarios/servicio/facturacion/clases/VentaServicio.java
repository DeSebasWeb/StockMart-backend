package sl.sistemaInventarios.servicio.facturacion.clases;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sl.sistemaInventarios.modelo.estado.EstadoEnum;
import sl.sistemaInventarios.modelo.facturacion.DetalleVenta;
import sl.sistemaInventarios.modelo.facturacion.Venta;
import sl.sistemaInventarios.modelo.producto.Producto;
import sl.sistemaInventarios.modelo.usuario.Vendedor;
import sl.sistemaInventarios.repositorio.facturacion.VentaRepositorio;
import sl.sistemaInventarios.servicio.estado.clases.EstadoServicio;
import sl.sistemaInventarios.servicio.facturacion.interfaces.IVentaServicio;
import sl.sistemaInventarios.servicio.producto.clases.ProductoServicio;

import java.util.Collections;
import java.util.List;

@Service
public class VentaServicio implements IVentaServicio {
    @Autowired
    private VentaRepositorio ventaRepositorio;

    private final EstadoServicio estadoServicio;

    private final ProductoServicio productoServicio;

    @Autowired
    public VentaServicio(EstadoServicio estadoServicio, ProductoServicio productoServicio) {
        this.estadoServicio = estadoServicio;
        this.productoServicio = productoServicio;
    }


    @Override
    public Venta guardarVenta(Venta venta) {
        Venta ventaGuardada = this.ventaRepositorio.save(venta);
        return  ventaGuardada;
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

    @Override
    public Venta softDelete(Venta venta) {
        Venta ventaEncontrada = this.buscarVentaPorId(venta);
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
        Venta ventaEncontrada = this.buscarVentaPorId(venta);
        if (ventaEncontrada != null){
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
    public Vendedor sumarVentaVendedor(Venta venta) {
        return null;
        //Necesita de la clase servicio de vendedor
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

    @Override
    public Venta realizarVenta(Venta venta) {

        return null;
    }

    @Override
    public void actualizarStock(Venta venta) {
        List<DetalleVenta> productosDetalleVenta = obtenerDetallesPorIdVenta(venta);
        if (!productosDetalleVenta.isEmpty()){
            productosDetalleVenta.forEach(productoDetalle ->{
                Producto producto = this.productoServicio.buscarProductoPorId(productoDetalle.getProducto());
                Integer Stock = producto.getStock();
                producto.setStock(Stock-productoDetalle.getCantidad());
                this.productoServicio.guardarProducto(producto);
            });
        }else {
            throw new RuntimeException("No hay ningun producto para actualizar el stock");
        }
    }
}