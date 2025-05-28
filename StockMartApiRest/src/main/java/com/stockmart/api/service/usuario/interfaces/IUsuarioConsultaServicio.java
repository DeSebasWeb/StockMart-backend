package com.stockmart.api.service.usuario.interfaces;

import com.stockmart.api.entity.estado.EstadoEnum;
import com.stockmart.api.entity.usuario.Usuario;

import java.util.List;

public interface IUsuarioConsultaServicio {
    public Usuario buscarUsuarioPorId(Usuario usuario);

    public Usuario buscarUsuarioPorCedula(Usuario usuario);

    public List<Usuario> mostrarUsuariosPorEstado(EstadoEnum estadoEnum);

    public Usuario buscarUsuarioPorCorreo(String correo);

    public List<Usuario> mostrarTodosUsuarios();
}
