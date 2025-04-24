package sl.sistemaInventarios.servicio.usuario.clases;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sl.sistemaInventarios.modelo.usuario.Usuario;
import sl.sistemaInventarios.repositorio.usuario.UsuarioRepositorio;
import sl.sistemaInventarios.servicio.estado.clases.EstadoServicio;
import sl.sistemaInventarios.servicio.seguridad.clases.EncriptacionServicio;
import sl.sistemaInventarios.servicio.usuario.interfaces.IUsuarioConsultaServicio;

@Service
@Transactional
public class UsuarioGestionServicio implements IUsuarioConsultaServicio {

    @Autowired
    private UsuarioRepositorio usuarioRepositorio;
    private final EstadoServicio estadoServicio;
    private final EncriptacionServicio encriptacionServicio;
    private final UsuarioConsultaServicio usuarioGestionServicio;
    @Autowired
    public UsuarioGestionServicio(EstadoServicio estadoServicio, EncriptacionServicio encriptacionServicio, UsuarioConsultaServicio usuarioGestionServicio) {
        this.estadoServicio = estadoServicio;
        this.encriptacionServicio = encriptacionServicio;
        this.usuarioGestionServicio = usuarioGestionServicio;
    }

    @Override
    public Usuario guardarUsuario(Usuario usuario) {
        if (usuario.getIdUsuario() == null){
            String passwordSegura = this.encriptacionServicio.hashPassword(usuario);
            usuario.setPassword(passwordSegura);
        }
        Usuario usuarioGuardado = this.usuarioRepositorio.save(usuario);
        return usuarioGuardado;
    }

    @Override
    public Usuario softDelete(Usuario usuario) {
        Usuario usuarioEncontrado = this.usuarioGestionServicio.buscarUsuarioPorId(usuario);
        if (usuarioEncontrado != null){
            if (usuarioEncontrado.getEstado().equals(this.estadoServicio.estaEstadoActivo().getEstado())){
                usuarioEncontrado.setEstado(this.estadoServicio.estaEstadoInactivo());
                Usuario usuarioGuardado = this.usuarioRepositorio.save(usuarioEncontrado);
                return usuarioGuardado;
            }else {
                return null;
            }
        }else {
            throw new RuntimeException("El usuario con id: "+usuario.getIdUsuario() +" no existe");
        }
    }

    @Override
    public void hardDelete(Usuario usuario) {
        Usuario usuarioEncontrado = this.usuarioGestionServicio.buscarUsuarioPorId(usuario);
        if (usuarioEncontrado!= null){
            if (usuarioEncontrado.getEstado().equals(this.estadoServicio.estaEstadoInactivo().getEstado())){
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
        Usuario usuarioEncontrado = this.usuarioGestionServicio.buscarUsuarioPorId(usuario);
        if (usuarioEncontrado!= null){
            if (usuarioEncontrado.getEstado().equals(this.estadoServicio.estaEstadoInactivo().getEstado())){
                usuarioEncontrado.setEstado(this.estadoServicio.estaEstadoActivo());
                Usuario usuarioGuardado = this.guardarUsuario(usuarioEncontrado);
                return usuarioGuardado;
            }else {
                throw new RuntimeException("El usuario esta activo, no es posible volverlo a activar");
            }
        }else {
            throw new RuntimeException("El usuario con id: "+usuario.getIdUsuario()+" no existe");
        }
    }
}
