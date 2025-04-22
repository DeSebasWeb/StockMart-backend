package sl.sistemaInventarios.servicio.categoriaProducto.clases;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sl.sistemaInventarios.modelo.categoriaProducto.ProductoCategoria;
import sl.sistemaInventarios.modelo.estado.EstadoEnum;
import sl.sistemaInventarios.repositorio.categoriaProducto.ProductoCategoriaRepositorio;
import sl.sistemaInventarios.servicio.categoriaProducto.interfaces.ICategoriaProductoServicio;
import sl.sistemaInventarios.servicio.estado.clases.EstadoServicio;

import java.util.List;

@Service
public class CategoriaProductoServicio implements ICategoriaProductoServicio {
    @Autowired
    private ProductoCategoriaRepositorio productoCategoriaRepositorio;

    @Autowired
    private EstadoServicio estadoServicio;

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
    public ProductoCategoria guardarOActualizarCategoria(ProductoCategoria productoCategoria) {
        ProductoCategoria productoCategoriaGuardar = this.productoCategoriaRepositorio.save(productoCategoria);
        return productoCategoriaGuardar;
    }

    //Cambia el estado de una categoría a INACTIVO (soft delete).
    @Override
    public ProductoCategoria softDelete(ProductoCategoria productoCategoria) {
        ProductoCategoria productoSoftDelete = this.buscarCategoriaPorId(productoCategoria);
        productoSoftDelete.setEstado(this.estadoServicio.estaEstadoInactivo());
        ProductoCategoria productoCategoriaGuardado = this.guardarOActualizarCategoria(productoSoftDelete);
        return productoCategoriaGuardado;
    }

    //Recupera los productosCategoria buscandolos y verificando si existen
    // y si existen y tienen el estado Inactivo los vuelve Activos
    @Override
    public ProductoCategoria recuperar(ProductoCategoria productoCategoria) {
        ProductoCategoria productoARecuperar = this.buscarCategoriaPorId(productoCategoria);
        if (productoARecuperar.getEstado() == this.estadoServicio.estaEstadoInactivo()){
            productoARecuperar.setEstado(this.estadoServicio.estaEstadoActivo());
        }
        return null;
    }

    @Override
    public void hardDelete(ProductoCategoria productoCategoria) {
        this.productoCategoriaRepositorio.delete(productoCategoria);
    }
}