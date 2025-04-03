package sl.sistemaInventarios.excepcion;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.ToString;

@Data
@AllArgsConstructor
@Getter
@ToString
public class MostrarMensajeExcepcion {
    private String mensaje;
}
