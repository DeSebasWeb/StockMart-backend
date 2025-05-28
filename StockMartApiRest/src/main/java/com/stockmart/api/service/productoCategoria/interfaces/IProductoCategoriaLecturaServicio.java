package com.stockmart.api.service.productoCategoria.interfaces;

import com.stockmart.api.dto.producto.ProductoDTO;
import com.stockmart.api.dto.productoCategoria.ProductoCategoriaCompletoDTO;
import com.stockmart.api.dto.productoCategoria.ProductoCategoriaDTO;
import com.stockmart.api.entity.productoCategoria.ProductoCategoria;
import com.stockmart.api.entity.estado.EstadoEnum;

import java.util.List;

public interface IProductoCategoriaLecturaServicio {
    public List<ProductoCategoriaDTO> mostrarTodasCategorias();

    public List<ProductoCategoriaDTO> mostrarCategoriasEstado(Integer idEstado);

    public ProductoCategoria buscarCategoriaPorId(Integer idCategoria);

    public ProductoCategoriaDTO buscarCategoriaPorIdADTO(Integer idCategoria);

    public List<ProductoDTO> productosAsociados(Integer idCategoria);

    public ProductoCategoriaCompletoDTO mostrarDetallesProductoCategoria(Integer idCategoria);
}
