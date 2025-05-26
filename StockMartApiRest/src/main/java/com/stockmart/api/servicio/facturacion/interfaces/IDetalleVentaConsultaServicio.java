package com.stockmart.api.servicio.facturacion.interfaces;

import com.stockmart.api.modelo.facturacion.DetalleVenta;
import com.stockmart.api.modelo.facturacion.Venta;

import java.util.List;

public interface IDetalleVentaConsultaServicio {
    public DetalleVenta mostrarDetalleVentaPorId(Integer idDetalle);

    public List<DetalleVenta> mostrarDetallesPorVenta(Venta venta);

    public List<DetalleVenta> mostrarTodosDetalles();
}