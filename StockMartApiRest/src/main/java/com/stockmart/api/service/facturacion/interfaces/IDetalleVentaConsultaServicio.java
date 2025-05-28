package com.stockmart.api.service.facturacion.interfaces;

import com.stockmart.api.entity.facturacion.DetalleVenta;
import com.stockmart.api.entity.facturacion.Venta;

import java.util.List;

public interface IDetalleVentaConsultaServicio {
    public DetalleVenta mostrarDetalleVentaPorId(Integer idDetalle);

    public List<DetalleVenta> mostrarDetallesPorVenta(Venta venta);

    public List<DetalleVenta> mostrarTodosDetalles();
}