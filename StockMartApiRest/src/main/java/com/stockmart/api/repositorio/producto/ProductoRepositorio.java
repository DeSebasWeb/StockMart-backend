package com.stockmart.api.repositorio.producto;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.stockmart.api.modelo.estado.Estado;
import com.stockmart.api.modelo.producto.Producto;

import java.util.List;

@Repository
public interface ProductoRepositorio extends JpaRepository<Producto, Integer> {
    List<Producto> findByEstado(Estado estado);

    List<Producto> findByProductoCategoria_Nombre(String nombreCategoria);

    // Consulta personalizada que devuelve los productos ordenados por cantidad vendida (descendente).
    // El uso de Pageable permite aplicar un límite dinámico de resultados directamente desde la base de datos.
    @Query("SELECT p FROM Producto p ORDER BY p.cantProductoVendido DESC")
    List<Producto> findTopProductos(Pageable pageable);
}
