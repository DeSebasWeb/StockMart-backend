package com.stockmart.api.service.productoCategoria.clases;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.stockmart.api.entity.estado.Estado;
import com.stockmart.api.entity.productoCategoria.ProductoCategoria;
import com.stockmart.api.repository.categoriaProducto.ProductoCategoriaRepositorio;
import com.stockmart.api.service.estado.clases.EstadoConsultaServicio;
import com.stockmart.api.service.productoCategoria.interfaces.IProductoCategoriaGestionServicio;
import com.stockmart.api.service.estado.clases.EstadoGestionServicio;

@Service
@Transactional
public class ProductoCategoriaGestionServicio implements IProductoCategoriaGestionServicio {
    private final ProductoCategoriaRepositorio productoCategoriaRepositorio;
    private final EstadoGestionServicio EstadoGestionServicio;
    private final ProductoCategoriaLecturaServicio productoCategoriaLecturaServicio;
    private final EstadoConsultaServicio EstadoConsultaServicio;

    @Autowired
    public ProductoCategoriaGestionServicio(ProductoCategoriaRepositorio productoCategoriaRepositorio, EstadoConsultaServicio EstadoConsultaServicio, EstadoGestionServicio EstadoGestionServicio, ProductoCategoriaLecturaServicio productoCategoriaLecturaServicio) {
        this.productoCategoriaRepositorio = productoCategoriaRepositorio;
        this.EstadoGestionServicio = EstadoGestionServicio;
        this.productoCategoriaLecturaServicio = productoCategoriaLecturaServicio;
        this.EstadoConsultaServicio = EstadoConsultaServicio;
    }

    @Override
    public ProductoCategoria guardarOActualizarCategoria(ProductoCategoria productoCategoria) {
        if(productoCategoria.getId() == null){
            Estado estadoEncontrado = this.EstadoConsultaServicio.buscarEstadoPorId(productoCategoria.getEstado().getIdEstado());
            if (estadoEncontrado == null){
                throw new RuntimeException("El id de estado que busca no existe");
            }else {
                productoCategoria.setEstado(estadoEncontrado);
                ProductoCategoria productoCategoriaGuardar = this.productoCategoriaRepositorio.save(productoCategoria);
                return productoCategoriaGuardar;
            }
        }else{
            ProductoCategoria productoCategoriaGuardar = this.productoCategoriaLecturaServicio.buscarCategoriaPorId(productoCategoria.getId());
            Estado estadoEncontrado = this.EstadoConsultaServicio.buscarEstadoPorId(productoCategoria.getEstado().getIdEstado());
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
    public ProductoCategoria softDelete(Integer idCategoria) {
        ProductoCategoria productoSoftDelete = this.productoCategoriaLecturaServicio.buscarCategoriaPorId(idCategoria);
        if (productoSoftDelete.getEstado().getIdEstado() == this.EstadoGestionServicio.estaEstadoInactivo().getIdEstado()){
            throw new RuntimeException("El producto no se encuentra activo");
        }else{
            productoSoftDelete.setEstado(this.EstadoGestionServicio.estaEstadoInactivo());
            ProductoCategoria productoCategoriaGuardado = this.guardarOActualizarCategoria(productoSoftDelete);
            return productoCategoriaGuardado;
        }
    }

    //Recupera los productosCategoria buscandolos y verificando si existen
    // y si existen y tienen el estado Inactivo los vuelve Activos
    @Override
    public ProductoCategoria recuperar(Integer idCategoria) {
        ProductoCategoria categoriaARecuperar = this.productoCategoriaLecturaServicio.buscarCategoriaPorId(idCategoria);
        if (categoriaARecuperar.getEstado() == this.EstadoGestionServicio.estaEstadoInactivo()){
            categoriaARecuperar.setEstado(this.EstadoGestionServicio.estaEstadoActivo());
            return this.guardarOActualizarCategoria(categoriaARecuperar);
        }else {
            throw new RuntimeException("La categoria se encuentra en estado activo");
        }
    }

    @Override
    public void hardDelete(Integer idCategoria) {
        ProductoCategoria CategoriaEncontrado = this.productoCategoriaLecturaServicio.buscarCategoriaPorId(idCategoria);
        if (CategoriaEncontrado.getEstado().getIdEstado() == this.EstadoGestionServicio.estaEstadoInactivo().getIdEstado()){
            this.productoCategoriaRepositorio.delete(CategoriaEncontrado);
        }else {
            throw new RuntimeException("La categoria se encuentra activa, descativela si quiere eliminarla permanentemente");
        }
    }
}