package sl.sistemaInventarios.servicio.usuario.clases;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sl.sistemaInventarios.modelo.usuario.Vendedor;
import sl.sistemaInventarios.repositorio.usuario.VendedorRepositorio;
import sl.sistemaInventarios.servicio.usuario.interfaces.IVendedorGestionServicio;

@Service
@Transactional
public class VendedorGestionServicio implements IVendedorGestionServicio {
    private final VendedorRepositorio vendedorRepositorio;
    private final VendedorConsultaServicio vendedorConsultaServicio;

    @Autowired
    public VendedorGestionServicio(VendedorRepositorio vendedorRepositorio, VendedorConsultaServicio vendedorConsultaServicio) {
        this.vendedorRepositorio = vendedorRepositorio;
        this.vendedorConsultaServicio = vendedorConsultaServicio;
    }

    @Override
    public Vendedor guardarDatosVendedor(Vendedor vendedor) {
        return this.vendedorRepositorio.save(vendedor);
    }

    @Override
    public Vendedor incrementarVenta(Vendedor vendedor) {
        Vendedor vendedorEncontrado = this.vendedorConsultaServicio.buscarVendedorPorId(vendedor);
        if (vendedorEncontrado != null){
            vendedorEncontrado.setNumeroVentas(vendedorEncontrado.getNumeroVentas()+1);
            return this.guardarDatosVendedor(vendedorEncontrado);
        }else {
            throw new RuntimeException("El vendedor con ID: "+ vendedor+ " no existe");
        }
    }
}
