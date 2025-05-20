package sl.sistemaInventarios.servicio.producto.clases;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sl.sistemaInventarios.modelo.estado.Estado;
import sl.sistemaInventarios.modelo.productoCategoria.ProductoCategoria;
import sl.sistemaInventarios.modelo.facturacion.DetalleVenta;
import sl.sistemaInventarios.modelo.facturacion.Venta;
import sl.sistemaInventarios.modelo.producto.Producto;
import sl.sistemaInventarios.repositorio.producto.ProductoRepositorio;
import sl.sistemaInventarios.servicio.productoCategoria.clases.ProductoCategoriaLecturaServicio;
import sl.sistemaInventarios.servicio.estado.clases.IEstadoConsultaServicio;
import sl.sistemaInventarios.servicio.estado.clases.IEstadoGestionServicio;
import sl.sistemaInventarios.servicio.facturacion.clases.VentaConsultaServicio;
import sl.sistemaInventarios.servicio.producto.interfaces.IProductoGestionServicio;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class ProductoGestionServicio implements IProductoGestionServicio {

    private final ProductoRepositorio productoRepositorio;

    private final ProductoLecturaServicio productoLecturaServicio;

    private final IEstadoGestionServicio estadoServicio;

    private final IEstadoConsultaServicio IEstadoConsultaServicio;

    private final ProductoCategoriaLecturaServicio productoCategoriaLecturaServicio;

    @Autowired
    public ProductoGestionServicio(ProductoLecturaServicio productoLecturaServicio, ProductoRepositorio productoRepositorio, IEstadoGestionServicio estadoServicio, IEstadoConsultaServicio IEstadoConsultaServicio, VentaConsultaServicio ventaConsultaServicio, ProductoCategoriaLecturaServicio productoCategoriaLecturaServicio) {
        this.productoLecturaServicio = productoLecturaServicio;
        this.productoRepositorio = productoRepositorio;
        this.estadoServicio = estadoServicio;
        this.productoCategoriaLecturaServicio = productoCategoriaLecturaServicio;
        this.IEstadoConsultaServicio = IEstadoConsultaServicio;
    }

    @Override
    public Producto guardarOActualizarProducto(Producto producto) {
        if (producto.getIdProducto() == null){
            ProductoCategoria productoCategoriaEncontrado = this.productoCategoriaLecturaServicio.buscarCategoriaPorId(producto.getProductoCategoria());
            Estado estadoEncontrado = this.IEstadoConsultaServicio.buscarEstadoPorId(producto.getEstado().getIdEstado());
            if (productoCategoriaEncontrado != null && estadoEncontrado!= null){
                producto.setProductoCategoria(productoCategoriaEncontrado);
                producto.setEstado(estadoEncontrado);
                Producto productoGuardado = this.productoRepositorio.save(producto);
                return productoGuardado;
            }else {
                throw new RuntimeException("No se encuentra la categoria o el estado a asignar. Verifique que exista y intentelo nuevamente");
            }
        }else {
            Producto productoAGuardar = this.productoLecturaServicio.buscarProductoPorId(producto.getIdProducto());
            if (productoAGuardar == null) {
                throw new RuntimeException("El producto a actualizar no existe, intentelo nuevamente");
            } else {
                ProductoCategoria productoCategoriaEncontrado = this.productoCategoriaLecturaServicio.buscarCategoriaPorId(producto.getProductoCategoria());
                Estado estadoEncontrado = this.IEstadoConsultaServicio.buscarEstadoPorId(producto.getEstado().getIdEstado());
                if (productoCategoriaEncontrado != null && estadoEncontrado != null) {
                    productoAGuardar.setNombre(producto.getNombre());
                    productoAGuardar.setDescripcion(producto.getDescripcion());
                    productoAGuardar.setProductoCategoria(productoCategoriaEncontrado);
                    productoAGuardar.setEstado(estadoEncontrado);
                    productoAGuardar.setStock(producto.getStock());
                    productoAGuardar.setPrecioCompra(producto.getPrecioCompra());
                    productoAGuardar.setPrecioVenta(producto.getPrecioVenta());
                    productoAGuardar.setMarca(producto.getMarca());
                    Producto productoGuardado = this.productoRepositorio.save(productoAGuardar);
                    return productoGuardado;
                } else {
                    throw new RuntimeException("No se encuentra la categoria o el estado a asignar. Verifique que exista y intentelo nuevamente");
                }
            }
        }
    }

    @Override
    public Producto softDelete(Integer idProducto) {
        Producto productoSoftDelete = this.productoLecturaServicio.buscarProductoPorId(idProducto);
        if (productoSoftDelete.getEstado().getIdEstado() == this.estadoServicio.estaEstadoActivo().getIdEstado()){
            productoSoftDelete.setEstado(this.IEstadoConsultaServicio.buscarEstadoPorId(2));
            Producto productoAlmacenado = this.guardarOActualizarProducto(productoSoftDelete);
            return productoAlmacenado;
        }else{
            throw new RuntimeException("No se puede eliminar el producto, ya se encuentra desactivado");
        }
    }

    @Override
    public Producto recuperarProducto(Integer idProducto) {
        Producto productoEncontrado = this.productoLecturaServicio.buscarProductoPorId(idProducto);
        if (productoEncontrado != null){
            if (productoEncontrado.getEstado().getIdEstado()==this.estadoServicio.estaEstadoInactivo().getIdEstado()){
                productoEncontrado.setEstado(this.estadoServicio.estaEstadoActivo());
                Producto productoActualizado = this.guardarOActualizarProducto(productoEncontrado);
                return productoActualizado;
            }else {
                throw new RuntimeException("El producto esta activo, no se puede activar dos veces");
            }
        }else {
            throw new RuntimeException("No existe ningun producto a recuperar");
        }
    }

    @Override
    public void hardDelete(Integer idProducto) {
        Producto productoEncontrado = this.productoLecturaServicio.buscarProductoPorId(idProducto);
        if (productoEncontrado!= null){
            if (productoEncontrado.getEstado().getIdEstado() == this.estadoServicio.estaEstadoInactivo().getIdEstado()){
                this.productoRepositorio.delete(productoEncontrado);
            }else{
                throw new RuntimeException("El producto no puede eliminarse pq esta activo, primero desactivelo");
            }
        }else{
            throw new RuntimeException("No se ha encontrado el producto a eliminar");
        }
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