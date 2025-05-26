package sl.sistemaInventarios.servicio.estado.interfaces;

import sl.sistemaInventarios.modelo.estado.Estado;

public interface IEstadoConsultaServicio {
    public Estado buscarEstadoPorId(Integer idEstado);
}
