package com.stockmart.api.servicio.estado.interfaces;

import com.stockmart.api.modelo.estado.Estado;

public interface IEstadoConsultaServicio {
    public Estado buscarEstadoPorId(Integer idEstado);
}
