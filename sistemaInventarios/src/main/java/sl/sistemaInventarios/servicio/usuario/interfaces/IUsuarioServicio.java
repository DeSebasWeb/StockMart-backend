package sl.sistemaInventarios.servicio.usuario.interfaces;

import sl.sistemaInventarios.modelo.usuario.Usuario;

import java.util.List;

public interface IUsuarioServicio {
    public List<Usuario> mostrarUsuarios();

    public Usuario guardarUsuario(Usuario usuario);

    public Usuario buscarUsuarioPorId(Integer idUsuario);

    public Usuario buscarUsuarioPorCedula(Long cedula);

    public void eliminarUsuario(Usuario usuario);


}