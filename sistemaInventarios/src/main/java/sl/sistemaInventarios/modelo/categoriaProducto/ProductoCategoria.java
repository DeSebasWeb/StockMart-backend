package sl.sistemaInventarios.modelo.categoriaProducto;

import jakarta.persistence.*;
import lombok.*;
import sl.sistemaInventarios.modelo.producto.Producto;

import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
@Table(name = "categoria")
public class ProductoCategoria {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_categoria")
    private Integer id;

    @Column(name = "nombre", nullable = false, unique = true)
    private String nombre;

    @Column(name = "descripcion")
    private String descripcion;

    @Enumerated(EnumType.STRING)
    @Column(name = "estado")
    private EstadoCategoria estado;

    // Relación con Productos (Opcional, pero útil)
    @OneToMany(mappedBy = "categoria", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Producto> productos;

//    nombre: Agregamos un campo para almacenar el nombre de la categoría. Sin esto, la tabla no sería útil.
//
//    @OneToMany(mappedBy = "categoria"): Relaciona Categoria con Productos, lo que permite obtener la lista de productos asociados a cada categoría.
//     cascade = CascadeType.ALL: Si eliminas una categoría, sus productos relacionados también pueden eliminarse automáticamente (opcional, depende de tu lógica de negocio).
//
//    orphanRemoval = true: Elimina productos si ya no tienen una categoría asociada.
}
