package sl.sistemaInventarios.modelo.usuario;

import jakarta.persistence.*;
import lombok.*;

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
    private Long idVendedor;

    @OneToOne
    @JoinColumn(name = "usuario_id", referencedColumnName = "id_usuario")
    private Usuario usuario_id;

    @Column(name = "numero_ventas")
    private long numeroVentas;

    @Column(name = "zona_trabajo")
    private String zonaTrabajo;
}
