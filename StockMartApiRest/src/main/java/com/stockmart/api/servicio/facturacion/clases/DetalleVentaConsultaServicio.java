package com.stockmart.api.servicio.facturacion.clases;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.stockmart.api.modelo.facturacion.DetalleVenta;
import com.stockmart.api.modelo.facturacion.Venta;
import com.stockmart.api.repositorio.facturacion.DetalleVentaRepositorio;
import com.stockmart.api.servicio.facturacion.interfaces.IDetalleVentaConsultaServicio;

import java.util.List;

@Service
public class DetalleVentaConsultaServicio implements IDetalleVentaConsultaServicio {
    @Autowired
    private DetalleVentaRepositorio detalleVentaRepositorio;

    @Override
    public DetalleVenta mostrarDetalleVentaPorId(Integer idDetalle) {
        return this.detalleVentaRepositorio.findById(idDetalle).orElse(null);
    }

    @Override
    public List<DetalleVenta> mostrarDetallesPorVenta(Venta venta) {
        return this.detalleVentaRepositorio.findByVenta(venta);
    }

    @Override
    public List<DetalleVenta> mostrarTodosDetalles() {
        return this.detalleVentaRepositorio.findAll();
    }
}
