package sl.sistemaInventarios.servicio.estado.interfaces;

import sl.sistemaInventarios.modelo.estado.Estado;

public interface IEstadoGestionServicio {
    public Estado estaEstadoInactivo();

    public Estado estaEstadoActivo();
}
