package com.stockmart.api.servicio.usuario.interfaces;

import com.stockmart.api.modelo.facturacion.Venta;
import com.stockmart.api.modelo.usuario.Usuario;
import com.stockmart.api.modelo.usuario.Vendedor;

public interface IVendedorGestionServicio {
    public Vendedor guardarDatosVendedor(Vendedor vendedor);

    public Vendedor incrementarVenta(Venta venta);

    public Vendedor crearDesdeUsuario(Usuario usuario);
}
