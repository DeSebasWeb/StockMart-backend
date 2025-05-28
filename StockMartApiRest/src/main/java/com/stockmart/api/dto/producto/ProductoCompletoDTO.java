package com.stockmart.api.dto.producto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import com.stockmart.api.entity.estado.Estado;

import java.time.LocalDateTime;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class ProductoCompletoDTO {
    private Integer idProducto;
    private String nombre;
    private String descripcion;
    private String nombreCategoria;
    private Estado estado;
    private Integer stock;
    private Integer precioVenta;
    private String marca;
    private LocalDateTime fechaRegistro;
    private LocalDateTime fechaModificacion;
    private LocalDateTime fechaEliminacion;
    private Integer cantProductoVendido;
}