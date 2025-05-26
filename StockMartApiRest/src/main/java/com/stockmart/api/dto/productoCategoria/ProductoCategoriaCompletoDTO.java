package com.stockmart.api.dto.productoCategoria;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class ProductoCategoriaCompletoDTO {
    private Integer idProductoCategoria;
    private String nombre;
    private String descripcion;
    private String nombreEstado;
    private Double precioMinimo;
    private LocalDateTime fechaCreacion;
    private LocalDateTime fechaModificacion;
    private LocalDateTime fechaEliminacion;
}
