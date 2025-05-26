package sl.sistemaInventarios.servicio.productoCategoria.clases;

import sl.sistemaInventarios.dto.productoCategoria.ProductoCategoriaCompletoDTO;
import sl.sistemaInventarios.dto.productoCategoria.ProductoCategoriaDTO;
import sl.sistemaInventarios.modelo.productoCategoria.ProductoCategoria;
import sl.sistemaInventarios.servicio.productoCategoria.interfaces.IConvertidorProductoADTOServicio;

import java.util.List;
import java.util.stream.Collectors;

public class ConvertidorProductoCategoriaADTOServicio implements IConvertidorProductoADTOServicio {
    @Override
    public ProductoCategoriaDTO convertirAProductoDTO(ProductoCategoria productoCategoria) {
        ProductoCategoriaDTO dto = new ProductoCategoriaDTO();
        dto.setIdProductoCategoria(productoCategoria.getId());
        dto.setNombre(productoCategoria.getNombre());
        dto.setDescripcion(productoCategoria.getDescripcion());
        if (productoCategoria.getEstado() != null){
            dto.setNombreEstado(productoCategoria.getEstado().getEstado().name());
        }
        dto.setPrecioMinimo(productoCategoria.getPrecioMinimo());
        return dto;
    }

    @Override
    public List<ProductoCategoriaDTO> convertirLista(List<ProductoCategoria> productoCategoria) {
        return productoCategoria.stream().map(this::convertirAProductoDTO).collect(Collectors.toList());
    }

    @Override
    public ProductoCategoriaCompletoDTO convertirAProductoCompletoDTO(ProductoCategoria productoCategoria) {
        ProductoCategoriaCompletoDTO dto = new ProductoCategoriaCompletoDTO();
        dto.setIdProductoCategoria(productoCategoria.getId());
        dto.setNombre(productoCategoria.getNombre());
        dto.setDescripcion(productoCategoria.getDescripcion());
        if (productoCategoria.getEstado() != null){
            dto.setNombreEstado(productoCategoria.getEstado().getEstado().name());
        }
        dto.setPrecioMinimo(productoCategoria.getPrecioMinimo());
        dto.setFechaCreacion(productoCategoria.getFechaCreacion());
        dto.setFechaModificacion(productoCategoria.getFechaModificacion());
        dto.setFechaEliminacion(productoCategoria.getFechaEliminacion());
        return dto;
    }
}
