package sl.sistemaInventarios.repositorio.facturacion;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sl.sistemaInventarios.modelo.facturacion.DetalleVenta;

import java.util.List;

@Repository
public interface DetalleVentaRepositorio extends JpaRepository<DetalleVenta, Integer> {
    List<DetalleVenta> findByVenta(DetalleVenta detalleVenta);
}
