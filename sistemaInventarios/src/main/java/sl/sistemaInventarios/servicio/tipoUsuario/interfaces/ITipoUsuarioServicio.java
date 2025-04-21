package sl.sistemaInventarios.servicio.tipoUsuario.interfaces;

import sl.sistemaInventarios.modelo.tipoUsuario.TipoUsuario;

import java.util.List;

public interface ITipoUsuarioServicio {
    public List<TipoUsuario> mostrarTiposDeUsuario();

    public TipoUsuario mostrarUsuarioPorID(TipoUsuario tipoUsuario);
}
