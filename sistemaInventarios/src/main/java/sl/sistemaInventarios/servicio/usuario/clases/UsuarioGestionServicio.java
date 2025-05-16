package sl.sistemaInventarios.servicio.usuario.clases;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sl.sistemaInventarios.modelo.estado.Estado;
import sl.sistemaInventarios.modelo.tipoUsuario.TipoUsuario;
import sl.sistemaInventarios.modelo.usuario.Usuario;
import sl.sistemaInventarios.repositorio.usuario.UsuarioRepositorio;
import sl.sistemaInventarios.servicio.estado.clases.EstadoConsultaServicio;
import sl.sistemaInventarios.servicio.estado.clases.EstadoGestionServicio;
import sl.sistemaInventarios.servicio.seguridad.clases.EncriptacionServicio;
import sl.sistemaInventarios.servicio.tipoUsuario.clases.TipoUsuarioConsultaServicio;
import sl.sistemaInventarios.servicio.usuario.interfaces.IUsuarioGestionServicio;

@Service
@Transactional
public class UsuarioGestionServicio implements IUsuarioGestionServicio {

    private final UsuarioRepositorio usuarioRepositorio;
    private final EstadoGestionServicio estadoServicio;
    private final EncriptacionServicio encriptacionServicio;
    private final UsuarioConsultaServicio usuarioConsultaServicio;
    private final TipoUsuarioConsultaServicio tipoUsuarioConsultaServicio;
    private final EstadoConsultaServicio estadoConsulta;

    @Autowired
    public UsuarioGestionServicio(EstadoGestionServicio estadoServicio, EncriptacionServicio encriptacionServicio, UsuarioConsultaServicio usuarioConsultaServicio, TipoUsuarioConsultaServicio tipoUsuarioConsultaServicio, UsuarioRepositorio usuarioRepositorio, EstadoConsultaServicio estadoConsulta) {
        this.estadoServicio = estadoServicio;
        this.encriptacionServicio = encriptacionServicio;
        this.usuarioConsultaServicio = usuarioConsultaServicio;
        this.tipoUsuarioConsultaServicio = tipoUsuarioConsultaServicio;
        this.usuarioRepositorio = usuarioRepositorio;
        this.estadoConsulta = estadoConsulta;
    }

    @Override
    public Usuario guardarUsuario(Usuario usuario) {
        if (usuario.getIdUsuario() == null){
            String passwordSegura = this.encriptacionServicio.hashPassword(usuario);
            usuario.setPassword(passwordSegura);

            TipoUsuario tipoUsuario = this.tipoUsuarioConsultaServicio.mostrarUsuarioPorID(usuario.getTipoUsuario().getId());
            usuario.setTipoUsuario(tipoUsuario);

            Estado estado = this.estadoConsulta.buscarEstadoPorId(usuario.getEstado().getIdEstado());
            usuario.setEstado(estado);

            Usuario usuarioGuardado = this.usuarioRepositorio.save(usuario);
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
            if (usuarioEncontrado.getEstado().getIdEstado() == this.estadoServicio.estaEstadoActivo().getIdEstado()){
                usuarioEncontrado.setEstado(this.estadoServicio.estaEstadoInactivo());
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
        Usuario usuarioEncontrado = this.usuarioConsultaServicio.buscarUsuarioPorId(usuario);
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

    @Override
    public boolean validacionCredenciales(String rawPassword, Usuario usuario) {
        return this.encriptacionServicio.matches(rawPassword, usuario);
    }
}