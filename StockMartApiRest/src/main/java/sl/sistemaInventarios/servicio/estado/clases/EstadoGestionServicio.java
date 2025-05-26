package sl.sistemaInventarios.servicio.estado.clases;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sl.sistemaInventarios.modelo.estado.Estado;
import sl.sistemaInventarios.modelo.estado.EstadoEnum;
import sl.sistemaInventarios.repositorio.estado.EstadoRepositorio;

@Service
public class EstadoGestionServicio implements sl.sistemaInventarios.servicio.estado.interfaces.IEstadoGestionServicio {
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
