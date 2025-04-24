package sl.sistemaInventarios.servicio.usuario.interfaces;

import sl.sistemaInventarios.modelo.usuario.Vendedor;

import java.util.List;

public interface IVendedorServicio {
    public Vendedor buscarVendedorPorId(Vendedor vendedor);

    public List<Vendedor> mostrarVendedores();

    public List<Vendedor> mostrarVendedoresEnZonaTrabajo(String zonaTrabajo);

    public Vendedor guardarDatosVendedor(Vendedor vendedor);
}
