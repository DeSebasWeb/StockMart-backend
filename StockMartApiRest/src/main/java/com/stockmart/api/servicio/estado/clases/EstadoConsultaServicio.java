package com.stockmart.api.servicio.estado.clases;

import com.stockmart.api.servicio.estado.interfaces.IEstadoConsultaServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.stockmart.api.modelo.estado.Estado;
import com.stockmart.api.repositorio.estado.EstadoRepositorio;

@Service
public class EstadoConsultaServicio implements IEstadoConsultaServicio {
    @Autowired
    private EstadoRepositorio estadoRepositorio;
    @Override
    public Estado buscarEstadoPorId(Integer idEstado) {
        return this.estadoRepositorio.findById(idEstado).orElseThrow(() -> new RuntimeException("No se ha encontrado ningun estado por el id "+idEstado));
    }
}
