package com.stockmart.api.entity.productoCategoria;

import jakarta.persistence.*;
import lombok.*;
import com.stockmart.api.entity.estado.Estado;
import com.stockmart.api.entity.producto.Producto;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
@Table(name = "producto_categoria")
public class ProductoCategoria {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_categoria")
    private Integer id;

    @Column(name = "nombre", nullable = false, unique = true)
    private String nombre;

    @ManyToOne
    @JoinColumn(name = "id_estado_categoria")
    private Estado estado;

    @Column(name = "descripcion")
    private String descripcion;

    @Column(name = "precio_minimo")
    private Double precioMinimo;

    @Column(name = "fecha_creacion", insertable = false, updatable = false)
    private LocalDateTime fechaCreacion;

    @Column(name = "fecha_modificacion", insertable = false, updatable = false)
    private LocalDateTime fechaModificacion;

    @Column(name = "fecha_eliminacion")
    private LocalDateTime fechaEliminacion;

    // Relación con Productos (Opcional, pero útil)
    @OneToMany(mappedBy = "productoCategoria")
    private List<Producto> productos;

//    nombre: Agregamos un campo para almacenar el nombre de la categoría. Sin esto, la tabla no sería útil.
//
//    @OneToMany(mappedBy = "categoria"): Relaciona Categoria con Productos, lo que permite obtener la lista de productos asociados a cada categoría.
//     cascade = CascadeType.ALL: Si eliminas una categoría, sus productos relacionados también pueden eliminarse automáticamente (opcional, depende de lógica de negocio).
//
//    orphanRemoval = true: Elimina productos si ya no tienen una categoría asociada.
}
