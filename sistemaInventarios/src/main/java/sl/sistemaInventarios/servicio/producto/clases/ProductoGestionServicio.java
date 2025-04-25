package sl.sistemaInventarios.servicio.producto.clases;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sl.sistemaInventarios.modelo.facturacion.DetalleVenta;
import sl.sistemaInventarios.modelo.facturacion.Venta;
import sl.sistemaInventarios.modelo.producto.Producto;
import sl.sistemaInventarios.repositorio.producto.ProductoRepositorio;
import sl.sistemaInventarios.servicio.estado.clases.EstadoServicio;
import sl.sistemaInventarios.servicio.facturacion.clases.DetalleVentaConsultaServicio;
import sl.sistemaInventarios.servicio.facturacion.clases.VentaConsultaServicio;
import sl.sistemaInventarios.servicio.producto.interfaces.IProductoGestionServicio;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class ProductoGestionServicio implements IProductoGestionServicio {

    private final ProductoRepositorio productoRepositorio;

    private final ProductoLecturaServicio productoLecturaServicio;

    private final DetalleVentaConsultaServicio detalleVentaConsultaServicio;

    private final EstadoServicio estadoServicio;

    private final VentaConsultaServicio ventaConsultaServicio;

    @Autowired
    public ProductoGestionServicio(ProductoLecturaServicio productoLecturaServicio, DetalleVentaConsultaServicio detalleVentaConsultaServicio, ProductoRepositorio productoRepositorio, EstadoServicio estadoServicio, VentaConsultaServicio ventaConsultaServicio) {
        this.productoLecturaServicio = productoLecturaServicio;
        this.detalleVentaConsultaServicio = detalleVentaConsultaServicio;
        this.productoRepositorio = productoRepositorio;
        this.estadoServicio = estadoServicio;
        this.ventaConsultaServicio = ventaConsultaServicio;
    }

    @Override
    public Producto softDelete(Producto producto) {
        Producto productoSoftDelete = this.productoLecturaServicio.buscarProductoPorId(producto);
        productoSoftDelete.setEstado(this.estadoServicio.estaEstadoInactivo());
        Producto productoGuardado = this.guardarProducto(productoSoftDelete);
        return productoGuardado;
    }

    @Override
    public Producto recuperarProducto(Producto producto) {
        return null;
    }

    @Override
    public void hardDelete(Producto producto) {
        this.productoRepositorio.delete(producto);
    }

    @Override
    public Producto guardarProducto(Producto producto) {
        Producto productoGuardado = this.productoRepositorio.save(producto);
        return productoGuardado;
    }
    @Override
    public void actualizarStock(Venta venta) {
        List<Producto> productosActualizados = new ArrayList<>();
        for (DetalleVenta detalleVenta : venta.getDetalles()){
            Producto producto = detalleVenta.getProducto();
            Integer cantVendida = detalleVenta.getCantidad();
            if (producto.getStock() < cantVendida){
                throw new IllegalArgumentException("Stock insuficiente para realizar la venta" + producto.getMarca());
            }
            producto.setStock(producto.getStock()-cantVendida);
            productosActualizados.add(producto);
        }
        this.productoRepositorio.saveAll(productosActualizados);
    }
}