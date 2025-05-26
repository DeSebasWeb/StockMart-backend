package com.stockmart.api.servicio.usuario.clases;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.stockmart.api.modelo.estado.Estado;
import com.stockmart.api.modelo.tipoUsuario.TipoUsuario;
import com.stockmart.api.modelo.usuario.Usuario;
import com.stockmart.api.repositorio.usuario.UsuarioRepositorio;
import com.stockmart.api.servicio.estado.clases.EstadoConsultaServicio;
import com.stockmart.api.servicio.estado.clases.EstadoGestionServicio;
import com.stockmart.api.servicio.seguridad.clases.IEncriptacionServicio;
import com.stockmart.api.servicio.tipoUsuario.clases.ITipoUsuarioConsultaServicio;

@Service
@Transactional
public class IUsuarioGestionServicio implements com.stockmart.api.servicio.usuario.interfaces.IUsuarioGestionServicio {

    private final UsuarioRepositorio usuarioRepositorio;
    private final EstadoGestionServicio estadoGestionServicio;
    private final IEncriptacionServicio encriptacionServicio;
    private final IUsuarioConsultaServicio usuarioConsultaServicio;
    private final ITipoUsuarioConsultaServicio tipoUsuarioConsultaServicio;
    private final EstadoConsultaServicio estadoConsultaServicio;
    private final IVendedorGestionServicio vendedorGestionServicio;

    @Autowired
    public IUsuarioGestionServicio(EstadoGestionServicio estadoGestionServicio, IVendedorGestionServicio vendedorGestionServicio, IEncriptacionServicio encriptacionServicio, IUsuarioConsultaServicio usuarioConsultaServicio, ITipoUsuarioConsultaServicio tipoUsuarioConsultaServicio, UsuarioRepositorio usuarioRepositorio, EstadoConsultaServicio estadoConsultaServicio) {
        this.estadoGestionServicio = estadoGestionServicio;
        this.encriptacionServicio = encriptacionServicio;
        this.usuarioConsultaServicio = usuarioConsultaServicio;
        this.tipoUsuarioConsultaServicio = tipoUsuarioConsultaServicio;
        this.usuarioRepositorio = usuarioRepositorio;
        this.estadoConsultaServicio = estadoConsultaServicio;
        this.vendedorGestionServicio = vendedorGestionServicio;
    }

    @Override
    public Usuario guardarUsuario(Usuario usuario) {
        if (usuario.getIdUsuario() == null){
            String passwordSegura = this.encriptacionServicio.hashPassword(usuario);
            usuario.setPassword(passwordSegura);

            TipoUsuario tipoUsuario = this.tipoUsuarioConsultaServicio.mostrarUsuarioPorID(usuario.getTipoUsuario().getId());
            usuario.setTipoUsuario(tipoUsuario);
            Estado estado = this.estadoConsultaServicio.buscarEstadoPorId(usuario.getEstado().getIdEstado());
            usuario.setEstado(estado);

            Usuario usuarioGuardado = this.usuarioRepositorio.save(usuario);

            // Si el tipo de usuario es VENDEDOR, se crea también la entidad Vendedor asociada.
            if (tipoUsuario.getId() == this.tipoUsuarioConsultaServicio.esVendedor().getId()){
                this.vendedorGestionServicio.crearDesdeUsuario(usuarioGuardado);
            }
            return usuarioGuardado;
        }else {
            Usuario usuarioEncontrado = this.usuarioConsultaServicio.buscarUsuarioPorId(usuario);
            usuarioEncontrado.setNombre(usuario.getNombre());
            usuarioEncontrado.setApellido(usuario.getApellido());
            usuarioEncontrado.setCedula(usuario.getCedula());
            usuarioEncontrado.setCorreo(usuario.getCorreo());
            String passwordSegura = this.encriptacionServicio.hashPassword(usuario);
            usuarioEncontrado.setPassword(passwordSegura);
            usuarioEncontrado.setTipoUsuario(usuario.getTipoUsuario());
            usuarioEncontrado.setEstado(usuario.getEstado());
            Usuario usuarioGuardado = this.usuarioRepositorio.save(usuarioEncontrado);
            return usuarioGuardado;
        }
    }

    @Override
    public Usuario softDelete(Usuario usuario) {
        Usuario usuarioEncontrado = this.usuarioConsultaServicio.buscarUsuarioPorId(usuario);
        if (usuarioEncontrado != null){
            if (usuarioEncontrado.getEstado().getIdEstado() == this.estadoGestionServicio.estaEstadoActivo().getIdEstado()){
                usuarioEncontrado.setEstado(this.estadoGestionServicio.estaEstadoInactivo());
                Usuario usuarioGuardado = this.usuarioRepositorio.save(usuarioEncontrado);
                return usuarioGuardado;
            }else {
                throw new RuntimeException("El usuario ya fue eliminado");
            }
        }else {
            throw new RuntimeException("El usuario con id: "+usuario.getIdUsuario() +" no existe");
        }
    }

    @Override
    public void hardDelete(Usuario usuario) {
        Usuario usuarioEncontrado = this.usuarioConsultaServicio.buscarUsuarioPorId(usuario);
        if (usuarioEncontrado!= null){
            if (usuarioEncontrado.getEstado().getIdEstado() == this.estadoGestionServicio.estaEstadoInactivo().getIdEstado()){
                this.usuarioRepositorio.delete(usuarioEncontrado);
            }else {
                throw new RuntimeException("El usuario tiene el estado Activo, si desea eliminarlo completamente, pongalo Inactivo");
            }
        }else {
            throw new RuntimeException("El usuario con id: "+usuario.getIdUsuario()+" no existe");
        }
    }

    @Override
    public Usuario recuperar(Usuario usuario) {
        Usuario usuarioEncontrado = this.usuarioConsultaServicio.buscarUsuarioPorId(usuario);
        if (usuarioEncontrado!= null){
            if (usuarioEncontrado.getEstado().getIdEstado() == this.estadoGestionServicio.estaEstadoInactivo().getIdEstado()){
                usuarioEncontrado.setEstado(this.estadoGestionServicio.estaEstadoActivo());
                Usuario usuarioGuardado = this.guardarUsuario(usuarioEncontrado);
                return usuarioGuardado;
            }else {
                throw new RuntimeException("El usuario esta activo, no es posible volverlo a activar");
            }
        }else {
            throw new RuntimeException("El usuario con id: "+usuario.getIdUsuario()+" no existe");
        }
    }

    @Override
    public boolean validacionCredenciales(String rawPassword, Usuario usuario) {
        return this.encriptacionServicio.matches(rawPassword, usuario);
    }
}