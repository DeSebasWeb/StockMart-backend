package com.stockmart.api.service.facturacion.clases;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.stockmart.api.entity.facturacion.DetalleVenta;
import com.stockmart.api.entity.facturacion.Venta;
import com.stockmart.api.repository.facturacion.DetalleVentaRepositorio;
import com.stockmart.api.service.facturacion.interfaces.IDetalleVentaConsultaServicio;

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
