package com.stockmart.api.service.productoCategoria.clases;

import com.stockmart.api.entity.estado.Estado;
import com.stockmart.api.service.estado.clases.EstadoConsultaServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.stockmart.api.dto.producto.ProductoDTO;
import com.stockmart.api.dto.productoCategoria.ProductoCategoriaCompletoDTO;
import com.stockmart.api.dto.productoCategoria.ProductoCategoriaDTO;
import com.stockmart.api.entity.producto.Producto;
import com.stockmart.api.entity.productoCategoria.ProductoCategoria;
import com.stockmart.api.entity.estado.EstadoEnum;
import com.stockmart.api.repository.categoriaProducto.ProductoCategoriaRepositorio;
import com.stockmart.api.service.producto.clases.ConvertidorProductoDTOServicio;
import com.stockmart.api.service.productoCategoria.interfaces.IProductoCategoriaLecturaServicio;
import com.stockmart.api.service.estado.clases.EstadoGestionServicio;

import java.util.List;

@Service
public class ProductoCategoriaLecturaServicio implements IProductoCategoriaLecturaServicio {


    private final ProductoCategoriaRepositorio productoCategoriaRepositorio;
    private final EstadoGestionServicio estadoGestionServicio;
    private final EstadoConsultaServicio estadoConsultaServicio;
    private final ConvertidorProductoCategoriaADTOServicio convertidorProductoCategoriaADTOServicio;
    private final ConvertidorProductoDTOServicio convertidorProductoDTOServicio;

    @Autowired
    public ProductoCategoriaLecturaServicio(EstadoConsultaServicio estadoConsultaServicio, ConvertidorProductoDTOServicio convertidorProductoDTOServicio, ProductoCategoriaRepositorio productoCategoriaRepositorio, EstadoGestionServicio estadoGestionServicio, ConvertidorProductoCategoriaADTOServicio convertidorProductoCategoriaADTOServicio) {
        this.productoCategoriaRepositorio = productoCategoriaRepositorio;
        this.estadoGestionServicio = estadoGestionServicio;
        this.convertidorProductoCategoriaADTOServicio = convertidorProductoCategoriaADTOServicio;
        this.convertidorProductoDTOServicio = convertidorProductoDTOServicio;
        this.estadoConsultaServicio = estadoConsultaServicio;
    }


    //Muestra todos los objetos de tipo ProductoCategoria
    @Override
    public List<ProductoCategoriaDTO> mostrarTodasCategorias() {
        List<ProductoCategoria> categorias = this.productoCategoriaRepositorio.findAll();
        return this.convertidorProductoCategoriaADTOServicio.convertirLista(categorias);
    }

    //Hago una busqueda con ayuda de Jpa para que me traiga todos los objetos de
    //CategoriaProducto dependiendo si se quiere que este activa o inactiva y lo devuelve como
    // lista para enviarlo al controlador
    @Override
    public List<ProductoCategoriaDTO> mostrarCategoriasEstado(Integer idEstado) {
        Estado estadoEncontrado = this.estadoConsultaServicio.buscarEstadoPorId(idEstado);
        if (estadoEncontrado !=null){
            List<ProductoCategoria> categorias = this.productoCategoriaRepositorio.findByEstado(estadoEncontrado);
            if (categorias!= null){
                if (!categorias.isEmpty()){
                    List<ProductoCategoriaDTO> categoriasDTO = this.convertidorProductoCategoriaADTOServicio.convertirLista(categorias);
                    return categoriasDTO;
                }else {
                    throw new RuntimeException("No hay ninguna categoria con ese estado");
                }
            }else {
                throw new RuntimeException("No se ha logrado encontrar las categorias con el estado indicado");
            }
        }else {
            throw new RuntimeException("El estado indicado no existe");
        }
    }

    @Override
    public ProductoCategoria buscarCategoriaPorId(Integer idCategoria) {
        ProductoCategoria productoCategoriaEncontradoId = this.productoCategoriaRepositorio.findById(idCategoria).orElseThrow(() -> new RuntimeException("Categoria no encontrada con ID: "+ idCategoria));
        return productoCategoriaEncontradoId;
    }

    public ProductoCategoriaDTO buscarCategoriaPorIdADTO(Integer idCategoria){
        ProductoCategoria productoCategoriaEncontradoId = this.productoCategoriaRepositorio.findById(idCategoria).orElseThrow(() -> new RuntimeException("Categoria no encontrada con ID: "+ idCategoria));
        return this.convertidorProductoCategoriaADTOServicio.convertirAProductoDTO(productoCategoriaEncontradoId);
    }

    @Override
    public List<ProductoDTO> productosAsociados(Integer idCategoria) {
        ProductoCategoria productoCategoriaEncontrado = this.buscarCategoriaPorId(idCategoria);
        if (productoCategoriaEncontrado != null){
            List<Producto> productos = productoCategoriaEncontrado.getProductos();
            return this.convertidorProductoDTOServicio.convertirLista(productos);
        }else {
            throw new RuntimeException("No existe la categoria");
        }
    }

    public ProductoCategoriaCompletoDTO mostrarDetallesProductoCategoria(Integer idCategoria){
        ProductoCategoria categoriaEncontrada = this.productoCategoriaRepositorio.findById(idCategoria).orElseThrow(() -> new RuntimeException("No se ha logrado encontrar la categoria con ese id"));
        return this.convertidorProductoCategoriaADTOServicio.convertirAProductoCompletoDTO(categoriaEncontrada);
    }
}
