package sl.sistemaInventarios.modelo.producto;

import jakarta.persistence.*;
import lombok.*;
import sl.sistemaInventarios.modelo.categoriaProducto.ProductoCategoria;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
@Table(name = "productos")
public class Producto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_producto")
    private Integer id;

    @Column(name = "nombre", nullable = false)
    private String nombre;

    @Column(name = "descripcion", nullable = false)
    private String descripcion;

    @ManyToOne
    @JoinColumn(name = "id_categoria", nullable = false)
    private ProductoCategoria productoCategoria;

    @Column(name = "stock", nullable = false)
    private Integer stock;

    @Column(name = "precio_compra", nullable = false)
    private Integer precioCompra;

    @Column(name = "precio_venta", nullable = false)
    private Integer precioVenta;

    @Column(name = "marca")
    private String marca;

    @Column(name = "fecha_creacion", insertable = false, updatable = false)
    private LocalDateTime fechaRegistro;

    @Column(name = "fecha_modificacion", insertable = false, updatable = false)
    private LocalDateTime fechaModificacion;

    @Column(name = "fecha_eliminacion")
    private LocalDateTime fechaEliminacion;
}
