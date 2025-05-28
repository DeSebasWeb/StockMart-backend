package com.stockmart.api.service.seguridad.interfaces;

import com.stockmart.api.entity.usuario.Usuario;

public interface IEncriptacionServicio {
    public String hashPassword(Usuario usuario);

    public boolean matches(String passwordInput, Usuario usuario);
}
