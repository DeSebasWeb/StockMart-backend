package sl.sistemaInventarios.servicio.productoCategoria.clases;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sl.sistemaInventarios.modelo.estado.Estado;
import sl.sistemaInventarios.modelo.productoCategoria.ProductoCategoria;
import sl.sistemaInventarios.repositorio.categoriaProducto.ProductoCategoriaRepositorio;
import sl.sistemaInventarios.servicio.estado.clases.IEstadoConsultaServicio;
import sl.sistemaInventarios.servicio.productoCategoria.interfaces.IProductoCategoriaGestionServicio;
import sl.sistemaInventarios.servicio.estado.clases.IEstadoGestionServicio;

@Service
@Transactional
public class ProductoCategoriaGestionServicio implements IProductoCategoriaGestionServicio {
    private final ProductoCategoriaRepositorio productoCategoriaRepositorio;
    private final IEstadoGestionServicio IEstadoGestionServicio;
    private final ProductoCategoriaLecturaServicio productoCategoriaLecturaServicio;
    private final IEstadoConsultaServicio IEstadoConsultaServicio;

    @Autowired
    public ProductoCategoriaGestionServicio(ProductoCategoriaRepositorio productoCategoriaRepositorio, IEstadoConsultaServicio IEstadoConsultaServicio, IEstadoGestionServicio IEstadoGestionServicio, ProductoCategoriaLecturaServicio productoCategoriaLecturaServicio) {
        this.productoCategoriaRepositorio = productoCategoriaRepositorio;
        this.IEstadoGestionServicio = IEstadoGestionServicio;
        this.productoCategoriaLecturaServicio = productoCategoriaLecturaServicio;
        this.IEstadoConsultaServicio = IEstadoConsultaServicio;
    }

    @Override
    public ProductoCategoria guardarOActualizarCategoria(ProductoCategoria productoCategoria) {
        if(productoCategoria.getId() == null){
            Estado estadoEncontrado = this.IEstadoConsultaServicio.buscarEstadoPorId(productoCategoria.getEstado().getIdEstado());
            if (estadoEncontrado == null){
                throw new RuntimeException("El id de estado que busca no existe");
            }else {
                productoCategoria.setEstado(estadoEncontrado);
                ProductoCategoria productoCategoriaGuardar = this.productoCategoriaRepositorio.save(productoCategoria);
                return productoCategoriaGuardar;
            }
        }else{
            ProductoCategoria productoCategoriaGuardar = this.productoCategoriaLecturaServicio.buscarCategoriaPorId(productoCategoria);
            Estado estadoEncontrado = this.IEstadoConsultaServicio.buscarEstadoPorId(productoCategoria.getEstado().getIdEstado());
            productoCategoriaGuardar.setNombre(productoCategoria.getNombre());
            productoCategoriaGuardar.setEstado(estadoEncontrado);
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
        if (productoSoftDelete.getEstado().getIdEstado() == this.IEstadoGestionServicio.estaEstadoInactivo().getIdEstado()){
            throw new RuntimeException("El producto no se encuentra activo");
        }else{
            productoSoftDelete.setEstado(this.IEstadoGestionServicio.estaEstadoInactivo());
            ProductoCategoria productoCategoriaGuardado = this.guardarOActualizarCategoria(productoSoftDelete);
            return productoCategoriaGuardado;
        }
    }

    //Recupera los productosCategoria buscandolos y verificando si existen
    // y si existen y tienen el estado Inactivo los vuelve Activos
    @Override
    public ProductoCategoria recuperar(ProductoCategoria productoCategoria) {
        ProductoCategoria productoARecuperar = this.productoCategoriaLecturaServicio.buscarCategoriaPorId(productoCategoria);
        if (productoARecuperar.getEstado() == this.IEstadoGestionServicio.estaEstadoInactivo()){
            productoARecuperar.setEstado(this.IEstadoGestionServicio.estaEstadoActivo());
        }
        return null;
    }

    @Override
    public void hardDelete(ProductoCategoria productoCategoria) {
        ProductoCategoria productoCategoriaEncontrado = this.productoCategoriaLecturaServicio.buscarCategoriaPorId(productoCategoria);
        if (productoCategoriaEncontrado.getEstado().getIdEstado() == this.IEstadoGestionServicio.estaEstadoInactivo().getIdEstado()){
            this.productoCategoriaRepositorio.delete(productoCategoria);
        }else {
            throw new RuntimeException("La categoria se encuentra activa, descativela si quiere eliminarla permanentemente");
        }
    }
}