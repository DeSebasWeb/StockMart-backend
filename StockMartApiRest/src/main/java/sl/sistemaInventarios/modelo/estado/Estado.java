package sl.sistemaInventarios.modelo.estado;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode

@Table(name = "estado")
public class Estado {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_estado")
    private Integer idEstado;

    @Enumerated(EnumType.STRING)
    @Column(name = "nombre_estado")
    private EstadoEnum estado;
}
