package com.stockmart.api.service.facturacion.interfaces;

import com.stockmart.api.entity.estado.EstadoEnum;
import com.stockmart.api.entity.facturacion.DetalleVenta;
import com.stockmart.api.entity.facturacion.Venta;
import com.stockmart.api.entity.usuario.Vendedor;

import java.util.List;

public interface IVentaConsultaServicio {
    public List<Venta> mostrarTodaVenta();

    public List<Venta> mostrarVentasPorEstado(EstadoEnum estadoEnum);

    public Venta buscarVentaPorId(Venta venta);

    public List<Venta> buscarVentasPorVendedor(Vendedor vendedor);

    public List<DetalleVenta> obtenerDetallesPorIdVenta(Venta venta);
}