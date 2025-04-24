package sl.sistemaInventarios.servicio.seguridad.clases;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import sl.sistemaInventarios.modelo.usuario.Usuario;
import sl.sistemaInventarios.servicio.seguridad.interfaces.IEncriptacionServicio;

@Service
public class EncriptacionServicio implements IEncriptacionServicio {

    private final PasswordEncoder passwordEncoder;

    @Autowired
    public EncriptacionServicio(PasswordEncoder passwordEncoder) {
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
