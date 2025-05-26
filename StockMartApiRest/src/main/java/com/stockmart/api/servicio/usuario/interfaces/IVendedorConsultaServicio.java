package com.stockmart.api.servicio.usuario.interfaces;

import com.stockmart.api.modelo.usuario.Vendedor;

import java.util.List;

public interface IVendedorConsultaServicio {
    public Vendedor buscarVendedorPorId(Integer idVendedor);

    public List<Vendedor> mostrarVendedores();

    public List<Vendedor> mostrarVendedoresEnZonaTrabajo(String zonaTrabajo);
}
