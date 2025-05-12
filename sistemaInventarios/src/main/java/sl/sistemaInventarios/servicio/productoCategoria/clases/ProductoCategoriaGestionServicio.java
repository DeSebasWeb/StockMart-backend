package sl.sistemaInventarios.servicio.productoCategoria.clases;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sl.sistemaInventarios.modelo.productoCategoria.ProductoCategoria;
import sl.sistemaInventarios.repositorio.categoriaProducto.ProductoCategoriaRepositorio;
import sl.sistemaInventarios.servicio.productoCategoria.interfaces.IProductoCategoriaGestionServicio;
import sl.sistemaInventarios.servicio.estado.clases.EstadoGestionServicio;

@Service
@Transactional
public class ProductoCategoriaGestionServicio implements IProductoCategoriaGestionServicio {
    private final ProductoCategoriaRepositorio productoCategoriaRepositorio;
    private final EstadoGestionServicio estadoServicio;
    private final ProductoCategoriaLecturaServicio productoCategoriaLecturaServicio;

    @Autowired
    public ProductoCategoriaGestionServicio(ProductoCategoriaRepositorio productoCategoriaRepositorio, EstadoGestionServicio estadoServicio, ProductoCategoriaLecturaServicio productoCategoriaLecturaServicio) {
        this.productoCategoriaRepositorio = productoCategoriaRepositorio;
        this.estadoServicio = estadoServicio;
        this.productoCategoriaLecturaServicio = productoCategoriaLecturaServicio;
    }

    @Override
    public ProductoCategoria guardarOActualizarCategoria(ProductoCategoria productoCategoria) {
        if(productoCategoria.getId() == null){
            ProductoCategoria productoCategoriaGuardar = this.productoCategoriaRepositorio.save(productoCategoria);
            return productoCategoriaGuardar;
        }else{
            ProductoCategoria productoCategoriaGuardar = this.productoCategoriaLecturaServicio.buscarCategoriaPorId(productoCategoria);
            productoCategoriaGuardar.setNombre(productoCategoria.getNombre());
            productoCategoriaGuardar.setEstado(productoCategoria.getEstado());
            productoCategoriaGuardar.setDescripcion(productoCategoria.getDescripcion());
            productoCategoriaGuardar.setPrecioMinimo(productoCategoria.getPrecioMinimo());
            ProductoCategoria productoGuardado = this.productoCategoriaRepositorio.save(productoCategoriaGuardar);
            return productoGuardado;
        }

    }

    //Cambia el estado de una categoría a INACTIVO (soft delete).
    @Override
    public ProductoCategoria softDelete(ProductoCategoria productoCategoria) {
        ProductoCategoria productoSoftDelete = this.productoCategoriaLecturaServicio.buscarCategoriaPorId(productoCategoria);
        productoSoftDelete.setEstado(this.estadoServicio.estaEstadoInactivo());
        ProductoCategoria productoCategoriaGuardado = this.guardarOActualizarCategoria(productoSoftDelete);
        return productoCategoriaGuardado;
    }

    //Recupera los productosCategoria buscandolos y verificando si existen
    // y si existen y tienen el estado Inactivo los vuelve Activos
    @Override
    public ProductoCategoria recuperar(ProductoCategoria productoCategoria) {
        ProductoCategoria productoARecuperar = this.productoCategoriaLecturaServicio.buscarCategoriaPorId(productoCategoria);
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