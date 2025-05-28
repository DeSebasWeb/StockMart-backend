package com.stockmart.api.service.facturacion.clases;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.stockmart.api.entity.estado.EstadoEnum;
import com.stockmart.api.entity.facturacion.DetalleVenta;
import com.stockmart.api.entity.facturacion.Venta;
import com.stockmart.api.entity.usuario.Vendedor;
import com.stockmart.api.repository.facturacion.VentaRepositorio;
import com.stockmart.api.service.estado.clases.EstadoGestionServicio;
import com.stockmart.api.service.facturacion.interfaces.IVentaConsultaServicio;
import com.stockmart.api.service.producto.clases.ProductoLecturaServicio;

import java.util.Collections;
import java.util.List;

@Service
public class VentaConsultaServicio implements IVentaConsultaServicio {

    private final VentaRepositorio ventaRepositorio;

    @Autowired
    public VentaConsultaServicio(EstadoGestionServicio estadoServicio, ProductoLecturaServicio productoServicio, VentaRepositorio ventaRepositorio) {
        this.ventaRepositorio = ventaRepositorio;
    }

    @Override
    public List<Venta> mostrarTodaVenta() {
        return this.ventaRepositorio.findAll();
    }

    @Override
    public List<Venta> mostrarVentasPorEstado(EstadoEnum estadoEnum) {
        List<Venta> ventasPorEstado = this.ventaRepositorio.findByEstado_Estado(estadoEnum);
        return ventasPorEstado;
    }

    @Override
    public Venta buscarVentaPorId(Venta venta) {
        Venta ventaEncontrada = this.ventaRepositorio.findById(venta.getIdVenta()).orElseThrow(() -> new RuntimeException("No se ha encontrado la venta con id: "+ venta.getIdVenta()));
        return ventaEncontrada;
    }

    @Override
    public List<Venta> buscarVentasPorVendedor(Vendedor vendedor) {
        List<Venta> ventasPorVendedor = this.ventaRepositorio.findByVendedor(vendedor);
        return ventasPorVendedor;
    }


    @Transactional
    @Override
    public List<DetalleVenta> obtenerDetallesPorIdVenta(Venta venta) {
        Venta ventaDetalles = this.buscarVentaPorId(venta);
        if (ventaDetalles != null){
            venta.getDetalles().size();
            return venta.getDetalles();
        }
        return Collections.emptyList();
    }
}