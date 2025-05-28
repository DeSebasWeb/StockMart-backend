package com.stockmart.api.service.productoCategoria.interfaces;

import com.stockmart.api.entity.productoCategoria.ProductoCategoria;

public interface IProductoCategoriaGestionServicio {
    public ProductoCategoria guardarOActualizarCategoria(ProductoCategoria productoCategoria);

    public ProductoCategoria softDelete(Integer idCategoria);

    public ProductoCategoria recuperar(Integer idCategoria);

    public void hardDelete(Integer idCategoria);
}
