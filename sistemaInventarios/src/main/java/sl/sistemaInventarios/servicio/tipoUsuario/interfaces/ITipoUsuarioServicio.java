package sl.sistemaInventarios.servicio.tipoUsuario.interfaces;

import sl.sistemaInventarios.modelo.estado.EstadoEnum;
import sl.sistemaInventarios.modelo.tipoUsuario.TipoUsuario;
import sl.sistemaInventarios.modelo.tipoUsuario.TipoUsuarioEnum;

import java.util.List;

public interface ITipoUsuarioServicio {
    public List<TipoUsuario> mostrarTiposDeUsuario();

    public TipoUsuario mostrarUsuarioPorID(Integer idTipoUsuario);

    public TipoUsuario esVendedor();

    public TipoUsuario esAdministrador();
}
