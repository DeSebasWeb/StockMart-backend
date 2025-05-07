package sl.sistemaInventarios.dto;

import lombok.*;

@Data
@ToString
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class CredencialesRespuesta {
    private String correo;
    private String password;
}
