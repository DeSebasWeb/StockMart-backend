package com.stockmart.api.service.usuario.interfaces;

import com.stockmart.api.entity.facturacion.Venta;
import com.stockmart.api.entity.usuario.Usuario;
import com.stockmart.api.entity.usuario.Vendedor;

public interface IVendedorGestionServicio {
    public Vendedor guardarDatosVendedor(Vendedor vendedor);

    public Vendedor incrementarVenta(Venta venta);

    public Vendedor crearDesdeUsuario(Usuario usuario);
}
