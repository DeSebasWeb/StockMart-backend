package com.stockmart.api.service.producto.interfaces;

import com.stockmart.api.dto.producto.ProductoCompletoDTO;
import com.stockmart.api.dto.producto.ProductoDTO;
import com.stockmart.api.entity.producto.Producto;

import java.util.List;

public interface IConvertidorProductoDTO {
    public ProductoDTO convertirAProductoDTO(Producto producto);

    public List<ProductoDTO> convertirLista(List<Producto> productos);

    public ProductoCompletoDTO convertirAProductoCompletoDTO(Producto producto);
}
