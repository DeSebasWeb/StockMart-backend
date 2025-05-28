package com.stockmart.api.service.facturacion.interfaces;

import com.stockmart.api.entity.facturacion.DetalleVenta;
import com.stockmart.api.entity.facturacion.Venta;

import java.util.List;

public interface IVentaGestionServicio {
    public Venta guardarVenta(Venta venta);

    public Venta softDelete(Venta venta);

    public void hardDelete(Venta venta);

    public Venta recuperar(Venta venta);

    public Venta realizarVenta(List<DetalleVenta> detalleVentas, Venta venta);

    public Venta calcularTotalVenta(Venta venta, List<DetalleVenta> detalleVentas);
}
