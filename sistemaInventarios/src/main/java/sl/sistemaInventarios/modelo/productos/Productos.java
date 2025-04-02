package sl.sistemaInventarios.modelo.productos;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
@Table(name = "productos")
public class Productos {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_producto")
    Integer id;
    @Column(name = "nombre", nullable = false)
    String nombre;
    @Column(name = "descripcion", nullable = false)
    String descripcion;
    @Column(name = "precio_compra", nullable = false)
    Integer precioCompra;
    @Column(name = "precio_venta", nullable = false)
    Integer precioVenta;
    @Column(name = "stock", nullable = false)
    Integer stock;
    @Column(name = "categoria")
    String categoria;
    @Column(name = "fecha_registro", insertable = false, updatable = false)
    private LocalDateTime fechaRegistro;
}
