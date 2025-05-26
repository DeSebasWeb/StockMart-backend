package com.stockmart.api.modelo.producto;

import jakarta.persistence.*;
import lombok.*;
import com.stockmart.api.modelo.productoCategoria.ProductoCategoria;
import com.stockmart.api.modelo.estado.Estado;
import com.stockmart.api.modelo.facturacion.DetalleVenta;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
@Table(name = "producto")
public class Producto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_producto")
    private Integer idProducto;

    @Column(name = "nombre", nullable = false)
    private String nombre;

    @Column(name = "descripcion")
    private String descripcion;

    @ManyToOne
    @JoinColumn(name = "id_categoria")
    private ProductoCategoria productoCategoria;

    @ManyToOne
    @JoinColumn(name = "id_estado")
    private Estado estado;

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

    @Column(name = "cant_producto_vendido")
    private Integer cantProductoVendido;

    @OneToMany(mappedBy = "producto")
    private List<DetalleVenta> detalles;
}
