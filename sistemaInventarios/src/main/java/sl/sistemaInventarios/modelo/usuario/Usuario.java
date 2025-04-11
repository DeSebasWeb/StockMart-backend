package sl.sistemaInventarios.modelo.usuario;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Table(name = "usuario")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_usuario")
    private Integer idUsuario;

    @Column(name = "nombre")
    private String nombre;

    @Column(name = "correo")
    private String correo;

    @Column(name = "password")
    private String password;

    @ManyToOne
    @Column(name = "tipo_usuario_id")
    private TipoUsuario tipoUsuarioId;
}
