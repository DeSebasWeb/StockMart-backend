package sl.sistemaInventarios.dto.credenciales;

import lombok.*;

@Data
@ToString
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class CredencialesRespuestaDTO {
    private String correo;
    private String password;
}
