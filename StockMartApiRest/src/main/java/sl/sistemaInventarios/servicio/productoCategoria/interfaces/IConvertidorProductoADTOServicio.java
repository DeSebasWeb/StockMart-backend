package sl.sistemaInventarios.servicio.productoCategoria.interfaces;

import sl.sistemaInventarios.dto.productoCategoria.ProductoCategoriaCompletoDTO;
import sl.sistemaInventarios.dto.productoCategoria.ProductoCategoriaDTO;
import sl.sistemaInventarios.modelo.productoCategoria.ProductoCategoria;

import java.util.List;

public interface IConvertidorProductoADTOServicio {
    public ProductoCategoriaDTO convertirAProductoDTO(ProductoCategoria productoCategoria);

    public List<ProductoCategoriaDTO> convertirLista(List<ProductoCategoria> productoCategoria);

    public ProductoCategoriaCompletoDTO convertirAProductoCompletoDTO(ProductoCategoria producto);
}
