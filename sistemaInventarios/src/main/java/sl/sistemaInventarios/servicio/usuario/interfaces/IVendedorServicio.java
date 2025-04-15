package sl.sistemaInventarios.servicio.usuario.interfaces;

import sl.sistemaInventarios.modelo.usuario.Vendedor;

import java.util.List;

public interface IVendedorServicio {

    public List<Vendedor> mostrarVendedores();

    public Vendedor guardarDatosVendedor(Vendedor vendedor);

    public Vendedor buscarVendedorPorId(Vendedor vendedor);

    public Vendedor eliminarVendedor(Vendedor vendedor);
}
