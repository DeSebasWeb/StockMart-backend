package sl.sistemaInventarios.servicio.estado.interfaces;

import sl.sistemaInventarios.modelo.estado.Estado;

public interface IEstadoConsulta {
    public Estado buscarEstadoPorId(Integer idEstado);
}
