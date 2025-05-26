package com.stockmart.api.servicio.tipoUsuario.interfaces;

import com.stockmart.api.modelo.tipoUsuario.TipoUsuario;

import java.util.List;

public interface ITipoUsuarioServicio {
    public List<TipoUsuario> mostrarTiposDeUsuario();

    public TipoUsuario mostrarUsuarioPorID(Integer idTipoUsuario);

    public TipoUsuario esVendedor();

    public TipoUsuario esAdministrador();
}
