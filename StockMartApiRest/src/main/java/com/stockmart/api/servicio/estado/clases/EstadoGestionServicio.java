package com.stockmart.api.servicio.estado.clases;

import com.stockmart.api.servicio.estado.interfaces.IEstadoGestionServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.stockmart.api.modelo.estado.Estado;
import com.stockmart.api.modelo.estado.EstadoEnum;
import com.stockmart.api.repositorio.estado.EstadoRepositorio;

@Service
public class EstadoGestionServicio implements IEstadoGestionServicio {
    @Autowired
    private EstadoRepositorio estadoRepositorio;

    @Override
    public Estado estaEstadoInactivo() {
        Estado estado = estadoRepositorio.findByEstado(EstadoEnum.INACTIVO).orElse(null);
        return estado;
    }

    @Override
    public Estado estaEstadoActivo() {
        Estado estado = estadoRepositorio.findByEstado(EstadoEnum.ACTIVO).orElse(null);
        return estado;
    }
}
