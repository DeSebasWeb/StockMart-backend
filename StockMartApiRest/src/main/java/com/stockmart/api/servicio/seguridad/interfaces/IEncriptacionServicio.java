package com.stockmart.api.servicio.seguridad.interfaces;

import com.stockmart.api.modelo.usuario.Usuario;

public interface IEncriptacionServicio {
    public String hashPassword(Usuario usuario);

    public boolean matches(String passwordInput, Usuario usuario);
}
