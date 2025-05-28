package com.stockmart.api.repository.facturacion;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.stockmart.api.entity.estado.EstadoEnum;
import com.stockmart.api.entity.facturacion.Venta;
import com.stockmart.api.entity.usuario.Vendedor;

import java.util.List;

@Repository
public interface VentaRepositorio extends JpaRepository<Venta, Integer> {
    List<Venta> findByVendedor(Vendedor vendedor);
    List<Venta> findByEstado_Estado(EstadoEnum estadoEnum);
}
