package sl.sistemaInventarios.servicio.producto.clases;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sl.sistemaInventarios.modelo.producto.Producto;
import sl.sistemaInventarios.repositorio.producto.ProductoRepositorio;
import sl.sistemaInventarios.servicio.producto.interfaces.IProductosServicio;

import java.util.List;

@Service
public class ProductosServicio implements IProductosServicio {
    @Autowired
    private ProductoRepositorio productoRepositorio;

    @Override
    public List<Producto> mostrarProductos() {
        return this.productoRepositorio.findAll();
    }

    @Override
    public Producto buscarProductoPorId(Integer id) {
        Producto producto = this.productoRepositorio.findById(id).orElse(null);
        return producto;
    }

    @Override
    public void eliminarProducto(Producto producto) {
        this.productoRepositorio.delete(producto);
    }

    @Override
    public List<Producto> buscarPorCategoria(String categoria) {
        List<Producto> productos = this.productoRepositorio.findByCategoria(categoria);
        return productos;
    }

    @Override
    public Producto guardarProducto(Producto producto) {
        Producto productoGuardado = this.productoRepositorio.save(producto);
        return productoGuardado;
    }
}
