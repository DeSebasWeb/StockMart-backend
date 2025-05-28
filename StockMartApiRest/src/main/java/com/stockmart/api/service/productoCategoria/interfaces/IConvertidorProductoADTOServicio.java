package com.stockmart.api.service.productoCategoria.interfaces;

import com.stockmart.api.dto.productoCategoria.ProductoCategoriaCompletoDTO;
import com.stockmart.api.dto.productoCategoria.ProductoCategoriaDTO;
import com.stockmart.api.entity.productoCategoria.ProductoCategoria;

import java.util.List;

public interface IConvertidorProductoADTOServicio {
    public ProductoCategoriaDTO convertirAProductoDTO(ProductoCategoria productoCategoria);

    public List<ProductoCategoriaDTO> convertirLista(List<ProductoCategoria> productoCategoria);

    public ProductoCategoriaCompletoDTO convertirAProductoCompletoDTO(ProductoCategoria producto);
}
