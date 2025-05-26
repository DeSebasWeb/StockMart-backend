package com.stockmart.api.servicio.producto.interfaces;

import com.stockmart.api.dto.producto.ProductoCompletoDTO;
import com.stockmart.api.dto.producto.ProductoDTO;
import com.stockmart.api.modelo.producto.Producto;

import java.util.List;

public interface IProductosLecturaServicio {
    public List<ProductoDTO> mostrarProductosPorEstado(Integer idEstado);

    public List<ProductoDTO> mostrarTodosLosProductos();

    public Producto buscarProductoPorId(Integer idProducto);

    public ProductoDTO buscarProductoPorIdADTO(Integer idProducto);

    public ProductoCompletoDTO buscarProductoPorIdConDetalles(Integer idProducto);

    public List<ProductoDTO> buscarPorCategoria(String categoria);

    public List<ProductoDTO> productosMasVendidos(int topN);
}
