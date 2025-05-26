package sl.sistemaInventarios.servicio.usuario.interfaces;

import sl.sistemaInventarios.modelo.usuario.Usuario;

public interface IUsuarioGestionServicio {
    public Usuario guardarUsuario(Usuario usuario);

    public Usuario softDelete(Usuario usuario);

    public void hardDelete(Usuario usuario);

    public Usuario recuperar(Usuario usuario);

    public boolean validacionCredenciales(String rawPassword, Usuario usuario);
}