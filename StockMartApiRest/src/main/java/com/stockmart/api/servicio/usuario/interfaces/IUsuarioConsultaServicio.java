package com.stockmart.api.servicio.usuario.interfaces;

import com.stockmart.api.modelo.estado.EstadoEnum;
import com.stockmart.api.modelo.usuario.Usuario;

import java.util.List;

public interface IUsuarioConsultaServicio {
    public Usuario buscarUsuarioPorId(Usuario usuario);

    public Usuario buscarUsuarioPorCedula(Usuario usuario);

    public List<Usuario> mostrarUsuariosPorEstado(EstadoEnum estadoEnum);

    public Usuario buscarUsuarioPorCorreo(String correo);

    public List<Usuario> mostrarTodosUsuarios();
}
