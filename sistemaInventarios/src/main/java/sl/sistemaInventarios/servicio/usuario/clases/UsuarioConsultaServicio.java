package sl.sistemaInventarios.servicio.usuario.clases;

import org.springframework.beans.factory.annotation.Autowired;
import sl.sistemaInventarios.modelo.estado.EstadoEnum;
import sl.sistemaInventarios.modelo.usuario.Usuario;
import sl.sistemaInventarios.repositorio.usuario.UsuarioRepositorio;
import sl.sistemaInventarios.servicio.usuario.interfaces.IUsuarioGestionServicio;

import java.util.List;

public class UsuarioConsultaServicio implements IUsuarioGestionServicio {
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
    public List<Usuario> mostrarTodosUsuarios() {
        return this.usuarioRepositorio.findAll();
    }
}
