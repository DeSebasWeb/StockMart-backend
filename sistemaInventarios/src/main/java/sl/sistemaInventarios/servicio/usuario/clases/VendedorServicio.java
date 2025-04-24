package sl.sistemaInventarios.servicio.usuario.clases;

import sl.sistemaInventarios.modelo.usuario.Vendedor;
import sl.sistemaInventarios.servicio.usuario.interfaces.IVendedorServicio;

import java.util.List;

public class VendedorServicio implements IVendedorServicio {
    @Override
    public Vendedor buscarVendedorPorId(Vendedor vendedor) {
        return null;
    }

    @Override
    public List<Vendedor> mostrarVendedores() {
        return List.of();
    }

    @Override
    public List<Vendedor> mostrarVendedoresEnZonaTrabajo(String zonaTrabajo) {
        return List.of();
    }

    @Override
    public Vendedor guardarDatosVendedor(Vendedor vendedor) {
        return null;
    }
}
