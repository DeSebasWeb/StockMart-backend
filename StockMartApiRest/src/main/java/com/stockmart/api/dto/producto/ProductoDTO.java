package com.stockmart.api.dto.producto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.stockmart.api.modelo.estado.Estado;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductoDTO {
    private Integer idProducto;
    private String nombre;
    private String descripcion;
    private String nombreCategoria;
    private Estado estado;
    private Integer stock;
    private Integer precioVenta;
    private String marca;
}
