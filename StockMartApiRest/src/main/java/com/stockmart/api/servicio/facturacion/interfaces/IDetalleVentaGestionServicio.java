package com.stockmart.api.servicio.facturacion.interfaces;

import com.stockmart.api.modelo.facturacion.DetalleVenta;
import com.stockmart.api.modelo.facturacion.Venta;

import java.util.List;

public interface IDetalleVentaGestionServicio {
    public List<DetalleVenta> guardarDetallesVenta(List<DetalleVenta> detallesVenta, Venta venta);

    public List<DetalleVenta> calcularSubtotalPorProducto(List<DetalleVenta> productosDetalleVenta);
}
