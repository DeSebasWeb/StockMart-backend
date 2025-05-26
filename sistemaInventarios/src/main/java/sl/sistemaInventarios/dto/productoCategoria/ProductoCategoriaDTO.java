package sl.sistemaInventarios.dto.productoCategoria;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.stereotype.Service;

@Service
@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class ProductoCategoriaDTO {
    private Integer idProductoCategoria;
    private String nombre;
    private String descripcion;
    private String nombreEstado;
    private Double precioMinimo;
}
