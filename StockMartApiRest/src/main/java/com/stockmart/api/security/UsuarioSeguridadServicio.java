package com.stockmart.api.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import com.stockmart.api.entity.usuario.Usuario;
import com.stockmart.api.repository.usuario.UsuarioRepositorio;

@Service

public class UsuarioSeguridadServicio implements UserDetailsService {
    @Autowired
    private UsuarioRepositorio usuarioRepositorio;

    @Override
    public UserDetails loadUserByUsername(String correo) throws UsernameNotFoundException {
        Usuario usuarioEncontrado = this.usuarioRepositorio.findByCorreo(correo).orElseThrow(() -> new RuntimeException("Usuario con el correo: " + correo+ " no existe"));
        //Aqui le doy a Spring security toda la informacion del usuario que se va a loguear
        return User.builder().username(usuarioEncontrado.getCorreo())
                //Contraseña
            .password(usuarioEncontrado.getPassword())
            .roles(usuarioEncontrado.getTipoUsuario().getNombre().toString()).build();
    }
}
