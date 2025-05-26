package sl.sistemaInventarios.servicio.producto.clases;

import org.springframework.stereotype.Service;
import sl.sistemaInventarios.dto.producto.ProductoCompletoDTO;
import sl.sistemaInventarios.dto.producto.ProductoDTO;
import sl.sistemaInventarios.modelo.producto.Producto;
import sl.sistemaInventarios.servicio.producto.interfaces.IConvertidorProductoDTO;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ConvertidorProductoDTOServicio implements IConvertidorProductoDTO {
    //Para enviar productos sin valores con tanta relevancia ni con valores sensibles convertirlo a dto
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
    public List<ProductoDTO> convertirLista(List<Producto> productos) {
        return productos.stream().map(this::convertirAProductoDTO).collect(Collectors.toList());
    }

    //Convertir Productos en ProductoDTO con detalles
    @Override
    public ProductoCompletoDTO convertirAProductoCompletoDTO(Producto producto) {
        ProductoCompletoDTO dto = new ProductoCompletoDTO();
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
        dto.setFechaRegistro(producto.getFechaRegistro());
        dto.setFechaModificacion(producto.getFechaModificacion());
        dto.setFechaEliminacion(producto.getFechaEliminacion());
        dto.setCantProductoVendido(producto.getCantProductoVendido());
        return dto;
    }
}