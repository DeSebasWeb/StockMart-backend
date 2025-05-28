package com.stockmart.api.service.tipoUsuario.clases;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.stockmart.api.entity.tipoUsuario.TipoUsuario;
import com.stockmart.api.entity.tipoUsuario.TipoUsuarioEnum;
import com.stockmart.api.repository.tipoUsuario.TipoUsuarioRespositorio;
import com.stockmart.api.service.tipoUsuario.interfaces.ITipoUsuarioServicio;

import java.util.List;

@Service
public class ITipoUsuarioConsultaServicio implements ITipoUsuarioServicio {
    @Autowired
    private TipoUsuarioRespositorio tipoUsuarioRespositorio;

    @Override
    public List<TipoUsuario> mostrarTiposDeUsuario() {
        return this.tipoUsuarioRespositorio.findAll();
    }

    @Override
    public TipoUsuario mostrarUsuarioPorID(Integer idTipoUsuario) {
        return this.tipoUsuarioRespositorio.findById(idTipoUsuario).orElse(null);
    }

    @Override
    public TipoUsuario esVendedor() {
        TipoUsuario vendedor = this.tipoUsuarioRespositorio.findByNombre(TipoUsuarioEnum.VENDEDOR).orElseThrow(()-> new RuntimeException("Error con cargar la info del vendedor"));
        return vendedor;
    }

    @Override
    public TipoUsuario esAdministrador() {
        TipoUsuario administrador = this.tipoUsuarioRespositorio.findByNombre(TipoUsuarioEnum.ADMINISTRADOR).orElseThrow(()-> new RuntimeException("Error con cargar la informacion del administrador"));
        return administrador;
    }
}
