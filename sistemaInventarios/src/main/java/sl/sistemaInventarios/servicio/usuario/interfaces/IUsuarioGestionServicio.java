package sl.sistemaInventarios.servicio.usuario.interfaces;

import sl.sistemaInventarios.modelo.estado.EstadoEnum;
import sl.sistemaInventarios.modelo.usuario.Usuario;

import java.util.List;

public interface IUsuarioGestionServicio {
    public Usuario buscarUsuarioPorId(Usuario usuario);

    public Usuario buscarUsuarioPorCedula(Usuario usuario);

    public List<Usuario> mostrarUsuariosPorEstado(EstadoEnum estadoEnum);

    public List<Usuario> mostrarTodosUsuarios();

}
