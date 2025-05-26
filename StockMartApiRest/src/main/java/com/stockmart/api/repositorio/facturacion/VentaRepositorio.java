package com.stockmart.api.repositorio.facturacion;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.stockmart.api.modelo.estado.EstadoEnum;
import com.stockmart.api.modelo.facturacion.Venta;
import com.stockmart.api.modelo.usuario.Vendedor;

import java.util.List;

@Repository
public interface VentaRepositorio extends JpaRepository<Venta, Integer> {
    List<Venta> findByVendedor(Vendedor vendedor);
    List<Venta> findByEstado_Estado(EstadoEnum estadoEnum);
}
