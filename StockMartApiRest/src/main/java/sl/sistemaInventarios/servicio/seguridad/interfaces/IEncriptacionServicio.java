package sl.sistemaInventarios.servicio.seguridad.interfaces;

import sl.sistemaInventarios.modelo.usuario.Usuario;

public interface IEncriptacionServicio {
    public String hashPassword(Usuario usuario);

    public boolean matches(String passwordInput, Usuario usuario);
}
