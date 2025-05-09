package sl.sistemaInventarios.servicio.producto.clases;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sl.sistemaInventarios.modelo.categoriaProducto.ProductoCategoria;
import sl.sistemaInventarios.modelo.facturacion.DetalleVenta;
import sl.sistemaInventarios.modelo.facturacion.Venta;
import sl.sistemaInventarios.modelo.producto.Producto;
import sl.sistemaInventarios.repositorio.producto.ProductoRepositorio;
import sl.sistemaInventarios.servicio.categoriaProducto.clases.ProductoCategoriaLecturaServicio;
import sl.sistemaInventarios.servicio.estado.clases.EstadoGestionServicio;
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

    private final EstadoGestionServicio estadoServicio;

    private final ProductoCategoriaLecturaServicio productoCategoriaLecturaServicio;

    @Autowired
    public ProductoGestionServicio(ProductoLecturaServicio productoLecturaServicio, ProductoRepositorio productoRepositorio, EstadoGestionServicio estadoServicio, VentaConsultaServicio ventaConsultaServicio, ProductoCategoriaLecturaServicio productoCategoriaLecturaServicio) {
        this.productoLecturaServicio = productoLecturaServicio;
        this.productoRepositorio = productoRepositorio;
        this.estadoServicio = estadoServicio;
        this.productoCategoriaLecturaServicio = productoCategoriaLecturaServicio;
    }

    @Override
    public Producto guardarProducto(Producto producto) {
        ProductoCategoria productoCategoriaEncontrado = this.productoCategoriaLecturaServicio.buscarCategoriaPorId(producto.getProductoCategoria());
        if (productoCategoriaEncontrado != null){
            producto.setProductoCategoria(productoCategoriaEncontrado);
            Producto productoGuardado = this.productoRepositorio.save(producto);
            return productoGuardado;
        }else {
            return null;
        }
    }

    @Override
    public Producto softDelete(Producto producto) {
        Producto productoSoftDelete = this.productoLecturaServicio.buscarProductoPorId(producto);
        productoSoftDelete.setEstado(this.estadoServicio.estaEstadoInactivo());
        Producto productoAlmacenado = this.guardarProducto(productoSoftDelete);
        return productoAlmacenado;
    }

    @Override
    public Producto recuperarProducto(Producto producto) {
        Producto productoEncontrado = this.productoLecturaServicio.buscarProductoPorId(producto);
        if (productoEncontrado != null){
            if (producto.getEstado().equals(this.estadoServicio.estaEstadoInactivo().getEstado())){
                productoEncontrado.setEstado(this.estadoServicio.estaEstadoActivo());
                Producto productoActualizado = this.guardarProducto(productoEncontrado);
                return productoActualizado;
            }else {
                throw new RuntimeException("El producto esta activo, no se puede activar dos veces");
            }
        }else {
            throw new RuntimeException("No existe ningun producto a recuperar");
        }
    }

    @Override
    public void hardDelete(Producto producto) {
        this.productoRepositorio.delete(producto);
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