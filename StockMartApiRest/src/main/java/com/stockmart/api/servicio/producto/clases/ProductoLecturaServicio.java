package com.stockmart.api.servicio.producto.clases;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import com.stockmart.api.dto.producto.ProductoCompletoDTO;
import com.stockmart.api.dto.producto.ProductoDTO;
import com.stockmart.api.modelo.estado.Estado;
import com.stockmart.api.modelo.producto.Producto;
import com.stockmart.api.repositorio.producto.ProductoRepositorio;
import com.stockmart.api.servicio.estado.clases.EstadoConsultaServicio;
import com.stockmart.api.servicio.estado.interfaces.IEstadoConsultaServicio;
import com.stockmart.api.servicio.producto.interfaces.IProductosLecturaServicio;

import java.util.List;

@Service
public class ProductoLecturaServicio implements IProductosLecturaServicio {
    private final ProductoRepositorio productoRepositorio;

    private final IEstadoConsultaServicio estadoConsultaServicio;

    private final ConvertidorProductoDTOServicio convertidorProductoDTOServicio;

    private static final Logger logger = LoggerFactory.getLogger(ProductoLecturaServicio.class);

    @Autowired
    public ProductoLecturaServicio(EstadoConsultaServicio estadoConsultaServicio, ProductoRepositorio productoRepositorio,  ConvertidorProductoDTOServicio convertidorProductoDTOServicio) {
        this.productoRepositorio = productoRepositorio;
        this.convertidorProductoDTOServicio = convertidorProductoDTOServicio;
        this.estadoConsultaServicio = estadoConsultaServicio;
    }

    @Override
    public List<ProductoDTO> mostrarProductosPorEstado(Integer idEstado) {
        Estado estadoEncontrado = this.estadoConsultaServicio.buscarEstadoPorId(idEstado);
        if (estadoEncontrado !=null){
            List<Producto> productos = this.productoRepositorio.findByEstado(estadoEncontrado);
            if(productos!= null){
                if (!productos.isEmpty()) {
                    List<ProductoDTO> productoDTOS = this.convertidorProductoDTOServicio.convertirLista(productos);
                    return productoDTOS;
                }else {
                    throw new RuntimeException("No hay ningun producto con ese estado");
                }
            }else {
                throw new RuntimeException("No se ha logrado encontrar los productos con el estado indicado");
            }
        }else {
            throw new RuntimeException("Error el estado indicado no existe");
        }
    }

    @Override
    public List<ProductoDTO> mostrarTodosLosProductos() {
        List<Producto> productos = this.productoRepositorio.findAll();
        return this.convertidorProductoDTOServicio.convertirLista(productos);
    }

    @Override
    public Producto buscarProductoPorId(Integer idProducto){
        Producto productoEncontrado = this.productoRepositorio.findById(idProducto).orElseThrow(()-> new RuntimeException("No se ha podido encontrar el producto"));
        return productoEncontrado;
    }

    @Override
    public ProductoDTO buscarProductoPorIdADTO(Integer idProducto) {
        Producto productoEncontrado = this.buscarProductoPorId(idProducto);
        ProductoDTO productoDTO = this.convertidorProductoDTOServicio.convertirAProductoDTO(productoEncontrado);
        return productoDTO;
    }

    @Override
    public ProductoCompletoDTO buscarProductoPorIdConDetalles(Integer idProducto) {
        Producto productoEncontrado = this.buscarProductoPorId(idProducto);
        ProductoCompletoDTO productoCompletoDTO = this.convertidorProductoDTOServicio.convertirAProductoCompletoDTO(productoEncontrado);
        return productoCompletoDTO;
    }

    @Override
    public List<ProductoDTO> buscarPorCategoria(String categoria) {
        List<Producto> productos = this.productoRepositorio.findByProductoCategoria_Nombre(categoria);
        if (!productos.isEmpty() && productos != null){
            logger.info("Se ha puesto en marcha el metodo");
            return this.convertidorProductoDTOServicio.convertirLista(productos);
        }else{
            throw new RuntimeException("No se ha podido encontrar ningun producto con la categoria que indica, Asegurese que exista en verdad");
        }
    }

    // Use Pageable para limitar la cantidad de productos traídos desde la base de datos.
    // PageRequest.of(0, topN) indica que quiero la "página 0" (es decir, desde el inicio)
    // con un tamaño de 'topN' elementos, ordenados por cantidad de productos vendidos.
    // Esto permite obtener eficientemente el Top N de productos más vendidos sin traer todos los registros.
    @Override
    public List<ProductoDTO> productosMasVendidos(int topN) {
        Pageable pageable = PageRequest.of(0, topN);
        List<Producto> topProductos = this.productoRepositorio.findTopProductos(pageable);
        return this.convertidorProductoDTOServicio.convertirLista(topProductos);
    }
}