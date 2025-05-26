package com.stockmart.api.servicio.estado.interfaces;

import com.stockmart.api.modelo.estado.Estado;

public interface IEstadoGestionServicio {
    public Estado estaEstadoInactivo();

    public Estado estaEstadoActivo();
}
