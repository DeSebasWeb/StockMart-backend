package sl.sistemaInventarios.servicio.productoCategoria.clases;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sl.sistemaInventarios.modelo.producto.Producto;
import sl.sistemaInventarios.modelo.productoCategoria.ProductoCategoria;
import sl.sistemaInventarios.modelo.estado.EstadoEnum;
import sl.sistemaInventarios.repositorio.categoriaProducto.ProductoCategoriaRepositorio;
import sl.sistemaInventarios.servicio.productoCategoria.interfaces.IProductoCategoriaLecturaServicio;
import sl.sistemaInventarios.servicio.estado.clases.IEstadoGestionServicio;

import java.util.List;

@Service
public class ProductoCategoriaLecturaServicio implements IProductoCategoriaLecturaServicio {
    @Autowired
    private ProductoCategoriaRepositorio productoCategoriaRepositorio;

    @Autowired
    private IEstadoGestionServicio estadoServicio;
    //Muestra todos los objetos de tipo ProductoCategoria
    @Override
    public List<ProductoCategoria> mostrarTodasCategorias() {
        return this.productoCategoriaRepositorio.findAll();
    }

    //Hago una busqueda con ayuda de Jpa para que me traiga todos los objetos de
    //CategoriaProducto dependiendo si se quiere que este activa o inactiva y lo devuelve como
    // lista para enviarlo al controlador
    @Override
    public List<ProductoCategoria> mostrarCategoriasEstado(EstadoEnum estadoEnum) {
        List<ProductoCategoria> productosCategorias = this.productoCategoriaRepositorio.findByEstado_Estado(estadoEnum);
        return productosCategorias;
    }

    @Override
    public ProductoCategoria buscarCategoriaPorId(ProductoCategoria productoCategoria) {
        ProductoCategoria productoCategoriaEncontradoId = this.productoCategoriaRepositorio.findById(productoCategoria.getId()).orElseThrow(() -> new RuntimeException("Categoria no encontrada con ID: "+ productoCategoria.getId()));
        return productoCategoriaEncontradoId;
    }
    @Override
    public List<Producto> productosAsociados(ProductoCategoria productoCategoria) {
        ProductoCategoria productoCategoriaEncontrado = this.buscarCategoriaPorId(productoCategoria);
        if (productoCategoriaEncontrado != null){
            return productoCategoriaEncontrado.getProductos();
        }else {
            throw new RuntimeException("No existe la categoria");
        }
    }
}
