package sl.sistemaInventarios.servicio.producto.clases;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import sl.sistemaInventarios.modelo.estado.EstadoEnum;
import sl.sistemaInventarios.modelo.producto.Producto;
import sl.sistemaInventarios.repositorio.producto.ProductoRepositorio;
import sl.sistemaInventarios.servicio.estado.clases.EstadoServicio;
import sl.sistemaInventarios.servicio.producto.interfaces.IProductosServicio;

import java.util.List;

@Service
public class ProductoServicio implements IProductosServicio {
    @Autowired
    private ProductoRepositorio productoRepositorio;

    private final EstadoServicio estadoServicio;

    @Autowired
    public ProductoServicio(EstadoServicio estadoServicio) {
        this.estadoServicio = estadoServicio;
    }


    @Override
    public List<Producto> mostrarProductosPorEstado(EstadoEnum estadoEnum) {
        List<Producto> productos = this.productoRepositorio.findByEstado_Estado(estadoEnum);
        return productos;
    }

    @Override
    public List<Producto> mostrarTodosLosProductos() {
        return this.productoRepositorio.findAll();
    }

    @Override
    public Producto buscarProductoPorId(Producto producto) {
        Producto productoEncontrado = this.productoRepositorio.findById(producto.getIdProducto()).orElseThrow(() -> new RuntimeException("No se ha encontrado ningun producto con el ID: "+ producto.getIdProducto()));
        return productoEncontrado;
    }

    @Override
    public List<Producto> buscarPorCategoria(String categoria) {
        List<Producto> productos = this.productoRepositorio.findByProductoCategoria_Nombre(categoria);
        return productos;
    }

    @Override
    public Producto softDelete(Producto producto) {
        Producto productoSoftDelete = this.buscarProductoPorId(producto);
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
    // Use Pageable para limitar la cantidad de productos traídos desde la base de datos.
    // PageRequest.of(0, topN) indica que quiero la "página 0" (es decir, desde el inicio)
    // con un tamaño de 'topN' elementos, ordenados por cantidad de productos vendidos.
    // Esto permite obtener eficientemente el Top N de productos más vendidos sin traer todos los registros.
    @Override
    public List<Producto> productosMasVendidos(int topN) {
        Pageable pageable = PageRequest.of(0, topN);
        return this.productoRepositorio.findTopProductos(pageable);
    }
}
