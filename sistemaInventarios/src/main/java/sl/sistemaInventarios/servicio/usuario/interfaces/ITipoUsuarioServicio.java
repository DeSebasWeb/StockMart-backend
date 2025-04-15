package sl.sistemaInventarios.servicio.usuario.interfaces;

import sl.sistemaInventarios.modelo.tipoUsuario.TipoUsuario;

import java.util.List;

public interface ITipoUsuarioServicio {
    public List<TipoUsuario> mostrarTiposUsuario();

    public TipoUsuario mostrarUsuarioPorID(TipoUsuario tipoUsuario);

}
