package sl.sistemaInventarios.servicio.usuario.clases;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sl.sistemaInventarios.modelo.usuario.Vendedor;
import sl.sistemaInventarios.repositorio.usuario.VendedorRepositorio;
import sl.sistemaInventarios.servicio.usuario.interfaces.IVendedorConsultaServicio;

import java.util.List;
@Service
public class VendedorConsultaServicio implements IVendedorConsultaServicio {
    @Autowired
    private VendedorRepositorio vendedorRepositorio;

    @Override
    public Vendedor buscarVendedorPorId(Integer idVendedor) {
        return this.vendedorRepositorio.findById(idVendedor).orElse(null);
    }

    @Override
    public List<Vendedor> mostrarVendedores() {
        return this.vendedorRepositorio.findAll();
    }

    @Override
    public List<Vendedor> mostrarVendedoresEnZonaTrabajo(String zonaTrabajo) {
        return this.vendedorRepositorio.findByZonaTrabajo(zonaTrabajo);
    }
}
