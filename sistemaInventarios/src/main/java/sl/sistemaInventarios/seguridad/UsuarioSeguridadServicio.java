package sl.sistemaInventarios.seguridad;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import sl.sistemaInventarios.modelo.usuario.Usuario;
import sl.sistemaInventarios.repositorio.usuario.UsuarioRepositorio;

@Service

public class UsuarioSeguridadServicio implements UserDetailsService {
    @Autowired
    private UsuarioRepositorio usuarioRepositorio;

    @Override
    @Bean
    public UserDetails loadUserByUsername(String correo) throws UsernameNotFoundException {
        Usuario usuarioEncontrado = this.usuarioRepositorio.findByCorreo(correo).orElseThrow(() -> new RuntimeException("Usuario con el correo: " + correo+ " no existe"));
        //Aqui le doy a Spring security toda la informacion del usuario que se va a loguear
        return User.builder().username(usuarioEncontrado.getCorreo())
                //Contraseña
            .password(usuarioEncontrado.getPassword())
            .roles("ROLE_"+usuarioEncontrado.getTipoUsuario().getNombre().toString()).build();
    }
}
