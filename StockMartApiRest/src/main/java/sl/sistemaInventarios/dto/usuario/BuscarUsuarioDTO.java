package sl.sistemaInventarios.dto.usuario;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class BuscarUsuarioDTO {
    private String correo;
    private Long cedula;
}
