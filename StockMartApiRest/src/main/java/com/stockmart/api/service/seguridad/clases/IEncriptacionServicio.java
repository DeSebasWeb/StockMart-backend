package com.stockmart.api.service.seguridad.clases;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.stockmart.api.entity.usuario.Usuario;

@Service
public class IEncriptacionServicio implements com.stockmart.api.service.seguridad.interfaces.IEncriptacionServicio {

    private final PasswordEncoder passwordEncoder;

    @Autowired
    public IEncriptacionServicio(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public String hashPassword(Usuario usuario) {
        String passwordHasheada = this.passwordEncoder.encode(usuario.getPassword());
        return passwordHasheada;
    }

    @Override
    public boolean matches(String rawPassword, Usuario usuario) {
        return passwordEncoder.matches(rawPassword, usuario.getPassword());
    }
}
