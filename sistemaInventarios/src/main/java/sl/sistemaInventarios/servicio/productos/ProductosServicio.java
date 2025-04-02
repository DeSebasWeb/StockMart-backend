package sl.sistemaInventarios.servicio.productos;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sl.sistemaInventarios.modelo.productos.Productos;
import sl.sistemaInventarios.repositorio.productos.ProductosRepositorio;

import java.util.List;

@Service
public class ProductosServicio implements IProductosServicio {
    @Autowired
    private ProductosRepositorio productosRepositorio;

    @Override
    public List<Productos> mostrarProductos() {
        return this.productosRepositorio.findAll();
    }

    @Override
    public Productos buscarProductoPorId(Integer id) {
        Productos producto = this.productosRepositorio.findById(id).orElse(null);
        return producto;
    }

    @Override
    public void eliminarProducto(Productos producto) {
        this.productosRepositorio.delete(producto);
    }

    @Override
    public List<Productos> buscarPorCategoria(String categoria) {
        List<Productos> productos = this.productosRepositorio.findByCategoria(categoria);
        return productos;
    }

    @Override
    public Productos guardarProducto(Productos producto) {
        Productos productoGuardado = this.productosRepositorio.save(producto);
        return productoGuardado;
    }
}
