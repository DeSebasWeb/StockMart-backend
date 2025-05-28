package com.stockmart.api.service.estado.clases;

import com.stockmart.api.service.estado.interfaces.IEstadoGestionServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.stockmart.api.entity.estado.Estado;
import com.stockmart.api.entity.estado.EstadoEnum;
import com.stockmart.api.repository.estado.EstadoRepositorio;

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
