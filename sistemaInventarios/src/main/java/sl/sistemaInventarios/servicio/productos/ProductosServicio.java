package sl.sistemaInventarios.servicio.productos;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sl.sistemaInventarios.modelo.producto.Producto;
import sl.sistemaInventarios.repositorio.producto.ProductosRepositorio;

import java.util.List;

@Service
public class ProductosServicio implements IProductosServicio {
    @Autowired
    private ProductosRepositorio productosRepositorio;

    @Override
    public List<Producto> mostrarProductos() {
        return this.productosRepositorio.findAll();
    }

    @Override
    public Producto buscarProductoPorId(Integer id) {
        Producto producto = this.productosRepositorio.findById(id).orElse(null);
        return producto;
    }

    @Override
    public void eliminarProducto(Producto producto) {
        this.productosRepositorio.delete(producto);
    }

    @Override
    public List<Producto> buscarPorCategoria(String categoria) {
        List<Producto> productos = this.productosRepositorio.findByCategoria(categoria);
        return productos;
    }

    @Override
    public Producto guardarProducto(Producto producto) {
        Producto productoGuardado = this.productosRepositorio.save(producto);
        return productoGuardado;
    }
}
