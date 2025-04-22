package sl.sistemaInventarios.servicio.usuario.interfaces;

import sl.sistemaInventarios.modelo.usuario.Usuario;

import java.util.List;

public interface IUsuarioServicio {
    public List<Usuario> mostrarUsuariosActivos();

    public List<Usuario> mostrarUsuariosInactivos();

    public Usuario guardarUsuario(Usuario usuario);

    public Usuario buscarUsuarioPorId(Integer idUsuario);

    public Usuario buscarUsuarioPorCedula(Long cedula);

    public Usuario softDelete(Usuario usuario);

    public Usuario hardDelete(Usuario usuario);

    public Usuario recuperar(Usuario usuario);
}