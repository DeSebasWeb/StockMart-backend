package com.stockmart.api.service.usuario.clases;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.stockmart.api.entity.estado.EstadoEnum;
import com.stockmart.api.entity.usuario.Usuario;
import com.stockmart.api.repository.usuario.UsuarioRepositorio;

import java.util.List;

@Service
public class IUsuarioConsultaServicio implements com.stockmart.api.service.usuario.interfaces.IUsuarioConsultaServicio {
    @Autowired
    private UsuarioRepositorio usuarioRepositorio;

    @Override
    public Usuario buscarUsuarioPorId(Usuario usuario) {
        Usuario usuarioEncontrado = this.usuarioRepositorio.findById(usuario.getIdUsuario()).orElse(null);
        return usuarioEncontrado;
    }

    @Override
    public Usuario buscarUsuarioPorCedula(Usuario usuario) {
        Usuario usuarioEncontrado = this.usuarioRepositorio.findByCedula(usuario.getCedula()).orElse(null);
        return usuarioEncontrado;
    }

    @Override
    public List<Usuario> mostrarUsuariosPorEstado(EstadoEnum estadoEnum) {
        List<Usuario> usuariosPorEstado = this.usuarioRepositorio.findByEstado_Estado(estadoEnum);
        if (usuariosPorEstado.isEmpty()){
            throw new RuntimeException("No se ha logrado encontrar ningun dato con el estado seleccionado");
        }else {
            return usuariosPorEstado;
        }
    }

    @Override
    public Usuario buscarUsuarioPorCorreo(String correo) {
        return this.usuarioRepositorio.findByCorreo(correo).orElseThrow(()-> new RuntimeException("No se encuentra el usuario con el correo indicado"));
    }

    @Override
    public List<Usuario> mostrarTodosUsuarios() {
        return this.usuarioRepositorio.findAll();
    }
}
