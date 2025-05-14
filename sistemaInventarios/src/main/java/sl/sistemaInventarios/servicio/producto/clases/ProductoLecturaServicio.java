package sl.sistemaInventarios.servicio.producto.clases;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import sl.sistemaInventarios.dto.producto.ProductoDTO;
import sl.sistemaInventarios.modelo.estado.EstadoEnum;
import sl.sistemaInventarios.modelo.producto.Producto;
import sl.sistemaInventarios.repositorio.producto.ProductoRepositorio;
import sl.sistemaInventarios.servicio.estado.clases.EstadoGestionServicio;
import sl.sistemaInventarios.servicio.producto.interfaces.IProductosLecturaServicio;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductoLecturaServicio implements IProductosLecturaServicio {
    @Autowired
    private ProductoRepositorio productoRepositorio;

    private final EstadoGestionServicio estadoServicio;

    @Autowired
    public ProductoLecturaServicio(EstadoGestionServicio estadoServicio) {
        this.estadoServicio = estadoServicio;
    }


    @Override
    public List<Producto> mostrarProductosPorEstado(EstadoEnum estadoEnum) {
        List<Producto> productos = this.productoRepositorio.findByEstado_Estado(estadoEnum);
        return productos;
    }

    @Override
    public List<Producto> mostrarTodosLosProductos() {
        return this.productoRepositorio.findAll();
    }

    @Override
    public Producto buscarProductoPorId(Producto producto) {
        Producto productoEncontrado = this.productoRepositorio.findById(producto.getIdProducto()).orElseThrow(() -> new RuntimeException("No se ha encontrado ningun producto con el ID: "+ producto.getIdProducto()));
        return productoEncontrado;
    }

    @Override
    public List<Producto> buscarPorCategoria(String categoria) {
        List<Producto> productos = this.productoRepositorio.findByProductoCategoria_Nombre(categoria);
        return productos;
    }

    // Use Pageable para limitar la cantidad de productos traídos desde la base de datos.
    // PageRequest.of(0, topN) indica que quiero la "página 0" (es decir, desde el inicio)
    // con un tamaño de 'topN' elementos, ordenados por cantidad de productos vendidos.
    // Esto permite obtener eficientemente el Top N de productos más vendidos sin traer todos los registros.
    @Override
    public List<Producto> productosMasVendidos(int topN) {
        Pageable pageable = PageRequest.of(0, topN);
        return this.productoRepositorio.findTopProductos(pageable);
    }

    //Para enviar productos sin valores con tanta relevancia ni con valores sensibles convertirlo a dto
    @Override
    public ProductoDTO convertirAProductoDTO(Producto producto){
        ProductoDTO dto = new ProductoDTO();
        dto.setIdProducto(producto.getIdProducto());
        dto.setNombre(producto.getNombre());
        dto.setDescripcion(producto.getDescripcion());
        if (producto.getProductoCategoria() != null){
            dto.setNombreCategoria(producto.getProductoCategoria().getNombre());
        }
        dto.setEstado(producto.getEstado());
        dto.setStock(producto.getStock());
        dto.setPrecioVenta(producto.getPrecioVenta());
        dto.setMarca(producto.getMarca());
        return dto;
    }

    //Convertir los producto en productoDTO
    @Override
    public List<ProductoDTO> convertirLista(List<Producto> productos) {
        return productos.stream().map(this::convertirAProductoDTO).collect(Collectors.toList());
    }
}