package com.stockmart.api.repository.facturacion;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.stockmart.api.entity.facturacion.DetalleVenta;
import com.stockmart.api.entity.facturacion.Venta;

import java.util.List;

@Repository
public interface DetalleVentaRepositorio extends JpaRepository<DetalleVenta, Integer> {
    List<DetalleVenta> findByVenta(Venta venta);
}
