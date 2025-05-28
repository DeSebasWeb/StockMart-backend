package com.stockmart.api.repository.categoriaProducto;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.stockmart.api.entity.productoCategoria.ProductoCategoria;
import com.stockmart.api.entity.estado.EstadoEnum;

import java.util.List;

@Repository
public interface ProductoCategoriaRepositorio extends JpaRepository<ProductoCategoria, Integer> {
    List<ProductoCategoria> findByEstado_Estado(EstadoEnum estadoEnum);
}
