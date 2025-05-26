package com.stockmart.api.servicio.productoCategoria.clases;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.stockmart.api.dto.producto.ProductoDTO;
import com.stockmart.api.dto.productoCategoria.ProductoCategoriaCompletoDTO;
import com.stockmart.api.dto.productoCategoria.ProductoCategoriaDTO;
import com.stockmart.api.modelo.producto.Producto;
import com.stockmart.api.modelo.productoCategoria.ProductoCategoria;
import com.stockmart.api.modelo.estado.EstadoEnum;
import com.stockmart.api.repositorio.categoriaProducto.ProductoCategoriaRepositorio;
import com.stockmart.api.servicio.producto.clases.ConvertidorProductoDTOServicio;
import com.stockmart.api.servicio.productoCategoria.interfaces.IProductoCategoriaLecturaServicio;
import com.stockmart.api.servicio.estado.clases.EstadoGestionServicio;

import java.util.List;

@Service
public class ProductoCategoriaLecturaServicio implements IProductoCategoriaLecturaServicio {


    private final ProductoCategoriaRepositorio productoCategoriaRepositorio;
    private final EstadoGestionServicio estadoServicio;
    private final ConvertidorProductoCategoriaADTOServicio convertidorProductoCategoriaADTOServicio;
    private final ConvertidorProductoDTOServicio convertidorProductoDTOServicio;

    @Autowired
    public ProductoCategoriaLecturaServicio(ConvertidorProductoDTOServicio convertidorProductoDTOServicio, ProductoCategoriaRepositorio productoCategoriaRepositorio, EstadoGestionServicio estadoServicio, ConvertidorProductoCategoriaADTOServicio convertidorProductoCategoriaADTOServicio) {
        this.productoCategoriaRepositorio = productoCategoriaRepositorio;
        this.estadoServicio = estadoServicio;
        this.convertidorProductoCategoriaADTOServicio = convertidorProductoCategoriaADTOServicio;
        this.convertidorProductoDTOServicio = convertidorProductoDTOServicio;
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
    public List<ProductoCategoriaDTO> mostrarCategoriasEstado(EstadoEnum estadoEnum) {
        List<ProductoCategoria> productosCategorias = this.productoCategoriaRepositorio.findByEstado_Estado(estadoEnum);
        return this.convertidorProductoCategoriaADTOServicio.convertirLista(productosCategorias);
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
