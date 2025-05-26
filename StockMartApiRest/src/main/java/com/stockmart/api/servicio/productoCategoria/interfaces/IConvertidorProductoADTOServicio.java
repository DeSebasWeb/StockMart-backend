package com.stockmart.api.servicio.productoCategoria.interfaces;

import com.stockmart.api.dto.productoCategoria.ProductoCategoriaCompletoDTO;
import com.stockmart.api.dto.productoCategoria.ProductoCategoriaDTO;
import com.stockmart.api.modelo.productoCategoria.ProductoCategoria;

import java.util.List;

public interface IConvertidorProductoADTOServicio {
    public ProductoCategoriaDTO convertirAProductoDTO(ProductoCategoria productoCategoria);

    public List<ProductoCategoriaDTO> convertirLista(List<ProductoCategoria> productoCategoria);

    public ProductoCategoriaCompletoDTO convertirAProductoCompletoDTO(ProductoCategoria producto);
}
