package sl.sistemaInventarios.servicio.tipoUsuario.clases;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sl.sistemaInventarios.modelo.tipoUsuario.TipoUsuario;
import sl.sistemaInventarios.repositorio.tipoUsuario.TipoUsuarioRespositorio;
import sl.sistemaInventarios.servicio.tipoUsuario.interfaces.ITipoUsuarioServicio;

import java.util.List;

@Service
public class TipoUsuarioConsultaServicio implements ITipoUsuarioServicio {
    @Autowired
    private TipoUsuarioRespositorio tipoUsuarioRespositorio;

    @Override
    public List<TipoUsuario> mostrarTiposDeUsuario() {
        return this.tipoUsuarioRespositorio.findAll();
    }

    @Override
    public TipoUsuario mostrarUsuarioPorID(Integer idTipoUsuario) {
        return this.tipoUsuarioRespositorio.findById(idTipoUsuario).orElse(null);
    }
}
