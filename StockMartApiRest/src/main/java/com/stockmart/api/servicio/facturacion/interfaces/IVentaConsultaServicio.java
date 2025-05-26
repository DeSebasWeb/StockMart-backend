package com.stockmart.api.servicio.facturacion.interfaces;

import com.stockmart.api.modelo.estado.EstadoEnum;
import com.stockmart.api.modelo.facturacion.DetalleVenta;
import com.stockmart.api.modelo.facturacion.Venta;
import com.stockmart.api.modelo.usuario.Vendedor;

import java.util.List;

public interface IVentaConsultaServicio {
    public List<Venta> mostrarTodaVenta();

    public List<Venta> mostrarVentasPorEstado(EstadoEnum estadoEnum);

    public Venta buscarVentaPorId(Venta venta);

    public List<Venta> buscarVentasPorVendedor(Vendedor vendedor);

    public List<DetalleVenta> obtenerDetallesPorIdVenta(Venta venta);
}