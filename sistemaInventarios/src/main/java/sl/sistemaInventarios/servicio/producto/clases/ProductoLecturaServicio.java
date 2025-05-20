package sl.sistemaInventarios.servicio.producto.clases;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import sl.sistemaInventarios.dto.producto.ProductoCompletoDTO;
import sl.sistemaInventarios.dto.producto.ProductoDTO;
import sl.sistemaInventarios.modelo.estado.EstadoEnum;
import sl.sistemaInventarios.modelo.producto.Producto;
import sl.sistemaInventarios.repositorio.producto.ProductoRepositorio;
import sl.sistemaInventarios.servicio.estado.clases.IEstadoGestionServicio;
import sl.sistemaInventarios.servicio.producto.interfaces.IProductosLecturaServicio;

import java.util.List;

@Service
public class ProductoLecturaServicio implements IProductosLecturaServicio {
    private final ProductoRepositorio productoRepositorio;

    private final IEstadoGestionServicio estadoServicio;

    private final ConvertidorProductoDTOServicio convertidorProductoDTOServicio;

    @Autowired
    public ProductoLecturaServicio(ProductoRepositorio productoRepositorio, IEstadoGestionServicio estadoServicio, ConvertidorProductoDTOServicio convertidorProductoDTOServicio) {
        this.estadoServicio = estadoServicio;
        this.productoRepositorio = productoRepositorio;
        this.convertidorProductoDTOServicio = convertidorProductoDTOServicio;
    }


    @Override
    public List<Producto> mostrarProductosPorEstado(EstadoEnum estadoEnum) {
        List<Producto> productos = this.productoRepositorio.findByEstado_Estado(estadoEnum);
        return productos;
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
}