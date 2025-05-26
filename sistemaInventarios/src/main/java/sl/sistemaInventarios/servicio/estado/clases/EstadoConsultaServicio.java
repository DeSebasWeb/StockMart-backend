package sl.sistemaInventarios.servicio.estado.clases;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sl.sistemaInventarios.modelo.estado.Estado;
import sl.sistemaInventarios.repositorio.estado.EstadoRepositorio;

@Service
public class EstadoConsultaServicio implements sl.sistemaInventarios.servicio.estado.interfaces.IEstadoConsultaServicio {
    @Autowired
    private EstadoRepositorio estadoRepositorio;
    @Override
    public Estado buscarEstadoPorId(Integer idEstado) {
        return this.estadoRepositorio.findById(idEstado).orElseThrow(() -> new RuntimeException("No se ha encontrado ningun estado por el id "+idEstado));
    }
}
