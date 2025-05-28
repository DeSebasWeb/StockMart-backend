package com.stockmart.api.service.tipoUsuario.interfaces;

import com.stockmart.api.entity.tipoUsuario.TipoUsuario;

import java.util.List;

public interface ITipoUsuarioServicio {
    public List<TipoUsuario> mostrarTiposDeUsuario();

    public TipoUsuario mostrarUsuarioPorID(Integer idTipoUsuario);

    public TipoUsuario esVendedor();

    public TipoUsuario esAdministrador();
}
