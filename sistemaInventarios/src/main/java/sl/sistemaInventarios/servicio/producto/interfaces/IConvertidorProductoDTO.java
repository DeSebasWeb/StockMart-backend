package sl.sistemaInventarios.servicio.producto.interfaces;

import sl.sistemaInventarios.dto.producto.ProductoCompletoDTO;
import sl.sistemaInventarios.dto.producto.ProductoDTO;
import sl.sistemaInventarios.modelo.producto.Producto;

import java.util.List;

public interface IConvertidorProductoDTO {
    public ProductoDTO convertirAProductoDTO(Producto producto);

    public List<ProductoDTO> convertirLista(List<Producto> productos);

    public ProductoCompletoDTO convertirAProductoCompletoDTO(Producto producto);
}
