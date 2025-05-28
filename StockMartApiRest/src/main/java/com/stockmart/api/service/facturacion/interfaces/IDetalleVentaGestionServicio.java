package com.stockmart.api.service.facturacion.interfaces;

import com.stockmart.api.entity.facturacion.DetalleVenta;
import com.stockmart.api.entity.facturacion.Venta;

import java.util.List;

public interface IDetalleVentaGestionServicio {
    public List<DetalleVenta> guardarDetallesVenta(List<DetalleVenta> detallesVenta, Venta venta);

    public List<DetalleVenta> calcularSubtotalPorProducto(List<DetalleVenta> productosDetalleVenta);
}
