package com.stockmart.api.service.producto.clases;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.stockmart.api.entity.estado.Estado;
import com.stockmart.api.entity.productoCategoria.ProductoCategoria;
import com.stockmart.api.entity.facturacion.DetalleVenta;
import com.stockmart.api.entity.facturacion.Venta;
import com.stockmart.api.entity.producto.Producto;
import com.stockmart.api.repository.producto.ProductoRepositorio;
import com.stockmart.api.service.productoCategoria.clases.ProductoCategoriaLecturaServicio;
import com.stockmart.api.service.estado.clases.EstadoConsultaServicio;
import com.stockmart.api.service.estado.clases.EstadoGestionServicio;
import com.stockmart.api.service.facturacion.clases.VentaConsultaServicio;
import com.stockmart.api.service.producto.interfaces.IProductoGestionServicio;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class ProductoGestionServicio implements IProductoGestionServicio {

    private final ProductoRepositorio productoRepositorio;

    private final ProductoLecturaServicio productoLecturaServicio;

    private final EstadoGestionServicio estadoServicio;

    private final EstadoConsultaServicio EstadoConsultaServicio;

    private final ProductoCategoriaLecturaServicio productoCategoriaLecturaServicio;

    @Autowired
    public ProductoGestionServicio(ProductoLecturaServicio productoLecturaServicio, ProductoRepositorio productoRepositorio, EstadoGestionServicio estadoServicio, EstadoConsultaServicio EstadoConsultaServicio, VentaConsultaServicio ventaConsultaServicio, ProductoCategoriaLecturaServicio productoCategoriaLecturaServicio) {
        this.productoLecturaServicio = productoLecturaServicio;
        this.productoRepositorio = productoRepositorio;
        this.estadoServicio = estadoServicio;
        this.productoCategoriaLecturaServicio = productoCategoriaLecturaServicio;
        this.EstadoConsultaServicio = EstadoConsultaServicio;
    }

    @Override
    public Producto guardarOActualizarProducto(Producto producto) {
        if (producto.getIdProducto() == null){
            ProductoCategoria productoCategoriaEncontrado = this.productoCategoriaLecturaServicio.buscarCategoriaPorId(producto.getProductoCategoria().getId());
            Estado estadoEncontrado = this.EstadoConsultaServicio.buscarEstadoPorId(producto.getEstado().getIdEstado());
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
                ProductoCategoria productoCategoriaEncontrado = this.productoCategoriaLecturaServicio.buscarCategoriaPorId(producto.getProductoCategoria().getId());
                Estado estadoEncontrado = this.EstadoConsultaServicio.buscarEstadoPorId(producto.getEstado().getIdEstado());
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
            productoSoftDelete.setEstado(this.EstadoConsultaServicio.buscarEstadoPorId(2));
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