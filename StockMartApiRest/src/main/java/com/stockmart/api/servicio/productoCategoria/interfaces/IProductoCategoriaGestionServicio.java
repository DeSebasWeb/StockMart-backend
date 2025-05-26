package com.stockmart.api.servicio.productoCategoria.interfaces;

import com.stockmart.api.modelo.productoCategoria.ProductoCategoria;

public interface IProductoCategoriaGestionServicio {
    public ProductoCategoria guardarOActualizarCategoria(ProductoCategoria productoCategoria);

    public ProductoCategoria softDelete(Integer idCategoria);

    public ProductoCategoria recuperar(Integer idCategoria);

    public void hardDelete(Integer idCategoria);
}
