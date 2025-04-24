package sl.sistemaInventarios.servicio.usuario.interfaces;

import sl.sistemaInventarios.modelo.estado.EstadoEnum;
import sl.sistemaInventarios.modelo.usuario.Usuario;

import java.util.List;

public interface IUsuarioConsultaServicio {
    public Usuario guardarUsuario(Usuario usuario);

    public Usuario softDelete(Usuario usuario);

    public void hardDelete(Usuario usuario);

    public Usuario recuperar(Usuario usuario);
}