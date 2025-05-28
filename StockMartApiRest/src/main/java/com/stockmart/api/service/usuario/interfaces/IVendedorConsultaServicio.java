package com.stockmart.api.service.usuario.interfaces;

import com.stockmart.api.entity.usuario.Vendedor;

import java.util.List;

public interface IVendedorConsultaServicio {
    public Vendedor buscarVendedorPorId(Integer idVendedor);

    public List<Vendedor> mostrarVendedores();

    public List<Vendedor> mostrarVendedoresEnZonaTrabajo(String zonaTrabajo);
}
