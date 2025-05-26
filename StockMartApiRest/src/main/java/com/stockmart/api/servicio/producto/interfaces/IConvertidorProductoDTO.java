package com.stockmart.api.servicio.producto.interfaces;

import com.stockmart.api.dto.producto.ProductoCompletoDTO;
import com.stockmart.api.dto.producto.ProductoDTO;
import com.stockmart.api.modelo.producto.Producto;

import java.util.List;

public interface IConvertidorProductoDTO {
    public ProductoDTO convertirAProductoDTO(Producto producto);

    public List<ProductoDTO> convertirLista(List<Producto> productos);

    public ProductoCompletoDTO convertirAProductoCompletoDTO(Producto producto);
}
