package sl.sistemaInventarios.modelo.facturacion;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
@Table(name = "detalle_venta")
public class DetalleVenta {
}
