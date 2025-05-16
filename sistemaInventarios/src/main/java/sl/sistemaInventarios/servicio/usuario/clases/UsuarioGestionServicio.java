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
    private final EstadoGestionServicio estadoGestionServicio;
    private final EncriptacionServicio encriptacionServicio;
    private final UsuarioConsultaServicio usuarioConsultaServicio;
    private final TipoUsuarioConsultaServicio tipoUsuarioConsultaServicio;
    private final EstadoConsultaServicio estadoConsultaServicio;

    @Autowired
    public UsuarioGestionServicio(EstadoGestionServicio estadoGestionServicio, EncriptacionServicio encriptacionServicio, UsuarioConsultaServicio usuarioConsultaServicio, TipoUsuarioConsultaServicio tipoUsuarioConsultaServicio, UsuarioRepositorio usuarioRepositorio, EstadoConsultaServicio estadoConsultaServicio) {
        this.estadoGestionServicio = estadoGestionServicio;
        this.encriptacionServicio = encriptacionServicio;
        this.usuarioConsultaServicio = usuarioConsultaServicio;
        this.tipoUsuarioConsultaServicio = tipoUsuarioConsultaServicio;
        this.usuarioRepositorio = usuarioRepositorio;
        this.estadoConsultaServicio = estadoConsultaServicio;
    }

    @Override
    public Usuario guardarUsuario(Usuario usuario) {

        if (usuario.getIdUsuario() == null){
            String passwordSegura = this.encriptacionServicio.hashPassword(usuario);
            usuario.setPassword(passwordSegura);

            TipoUsuario tipoUsuario = this.tipoUsuarioConsultaServicio.mostrarUsuarioPorID(usuario.getTipoUsuario().getId());
            usuario.setTipoUsuario(tipoUsuario);
            if (tipoUsuario.getId() == this.tipoUsuarioConsultaServicio.esVendedor().getId()){
                //Poner nueva construccion de un vendedor
            }
            Estado estado = this.estadoConsultaServicio.buscarEstadoPorId(usuario.getEstado().getIdEstado());
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