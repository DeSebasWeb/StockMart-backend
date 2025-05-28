package com.stockmart.api.service.estado.interfaces;

import com.stockmart.api.entity.estado.Estado;

public interface IEstadoGestionServicio {
    public Estado estaEstadoInactivo();

    public Estado estaEstadoActivo();
}
