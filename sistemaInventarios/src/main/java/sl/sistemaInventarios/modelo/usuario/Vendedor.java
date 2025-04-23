package sl.sistemaInventarios.modelo.usuario;

import jakarta.persistence.*;
import lombok.*;
import sl.sistemaInventarios.modelo.facturacion.Venta;

import java.util.List;

@Entity
@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class Vendedor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "vendedor_id")
    private Integer idVendedor;

    @OneToOne
    @JoinColumn(name = "usuario_id", referencedColumnName = "id_usuario")
    private Usuario usuario;

    @Column(name = "numero_ventas")
    private long numeroVentas;

    @Column(name = "zona_trabajo")
    private String zonaTrabajo;

    @OneToMany(mappedBy = "vendedor")
    private List<Venta> ventas;
}
